package com.phuclq.student.controller;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phuclq.student.component.RestEntityResponse;
import com.phuclq.student.domain.File;
import com.phuclq.student.domain.User;
import com.phuclq.student.domain.UserHistory;
import com.phuclq.student.domain.UserHistoryFile;
import com.phuclq.student.momo.allinone.models.CaptureMoMoRequest;
import com.phuclq.student.momo.allinone.models.CaptureMoMoResponse;
import com.phuclq.student.momo.shared.constants.Parameter;
import com.phuclq.student.momo.shared.utils.Encoder;
import com.phuclq.student.repository.FileRepository;
import com.phuclq.student.repository.UserHistoryFileRepository;
import com.phuclq.student.service.ConfirmationTokenService;
import com.phuclq.student.service.EmailSenderService;
import com.phuclq.student.service.UserHistoryService;
import com.phuclq.student.service.UserService;
import com.phuclq.student.utils.ActivityConstants;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@RestController
public class RechangreViaMomoController {
	public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

	ObjectMapper mapper = new ObjectMapper();
	@Value("${momo.endpoint}")
	private String baseUrl;
	@Value("${momo.pay-gate}")
	private String paygate;
	@Value("${momo.accesskey}")
	private String accessKey;
	@Value("${momo.serect-key}")
	private String serectkey;
	@Value("${momo.partner-code}")
	private String partnerCode;
	
	@Value("${momo.returnURL}")
	private String returnURL;
	@Value("${momo.notifyURL}")
	private String notifyURL;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private RestEntityResponse restEntityRes;
	
	@Autowired
	private UserHistoryService userHistoryService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private ConfirmationTokenService confirmationTokenService;
	
	@Autowired
	private UserHistoryFileRepository userHistoryFileRepository;
	
	@Autowired
	private FileRepository fileRepository;
	
	@Autowired
    private EmailSenderService emailSenderService;
	
	@CrossOrigin
	@GetMapping("/api/momo")
	public ResponseEntity<?> callMomoQr(@RequestParam Integer id ,@RequestParam Long amount, HttpServletResponse httpResponse) throws IOException, InvalidKeyException, NoSuchAlgorithmException {
		String url = baseUrl + paygate;
		String requestId = String.valueOf(System.currentTimeMillis());
		String orderId = String.valueOf(System.currentTimeMillis());
		String orderInfo = "Pay With MoMo";
		String extraData = "";
		String requestType = "captureMoMoWallet";
		String message = "";
		if (validateAmount(amount) == false) {
			message = "Giá trị tiền nạp vào cần là bội số của 1000 VNĐ";
			return restEntityRes.setHttpStatus(HttpStatus.ACCEPTED).setDataResponse(message).getResponse();
		}
		
		String rawData = Parameter.PARTNER_CODE + "=" + partnerCode + "&" + Parameter.ACCESS_KEY + "=" + accessKey + "&"
				+ Parameter.REQUEST_ID + "=" + requestId + "&" + Parameter.AMOUNT + "=" + amount + "&"
				+ Parameter.ORDER_ID + "=" + orderId + "&" + Parameter.ORDER_INFO + "=" + orderInfo + "&"
				+ Parameter.RETURN_URL + "=" + returnURL + "&" + Parameter.NOTIFY_URL + "=" + notifyURL + "&"
				+ Parameter.EXTRA_DATA + "=" + extraData;

		String signature = Encoder.signHmacSHA256(rawData, serectkey);
		
		User user = userService.getUserLogin();
		UserHistory userHistory = userHistoryService.activateFileHistory(user.getId(), id, ActivityConstants.DOWNLOAD);
		if (userHistory != null) {
			userHistory.setOrderId(orderId);
			userHistory.setStatus(1);
			userHistoryService.saveUserHistory(userHistory);
			Request request = null;
			/* Lưu thông tin order ID vào userhistoryfile
			 * 1. Lấy thông tin UserhistoryFile
			 * 2. Lưu orderID vào
			 */
			CaptureMoMoRequest captureMoMoRequest = new CaptureMoMoRequest(partnerCode, orderId, orderInfo, accessKey,
					String.valueOf(amount), signature, extraData, requestId, notifyURL, returnURL, requestType);
			String json = mapper.writeValueAsString(captureMoMoRequest);
			OkHttpClient client = new OkHttpClient();
			HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();
			RequestBody body;
			if (json == null) {
				body = RequestBody.create(null, new byte[]{});
			} else
				body = RequestBody.create(JSON, json);
			request = new Request.Builder().url(httpBuilder.build()).post(body).build();
			Response response = client.newCall(request).execute();
			String strRes = response.body().string();
			strRes = strRes.replace("null", "\"\"");
			CaptureMoMoResponse captureMoMoResponse = mapper.readValue(strRes, CaptureMoMoResponse.class);
			String redirectUrl = captureMoMoResponse.getPayUrl();
			//httpResponse.sendRedirect(redirectUrl);
			message = "Thành công!";
			return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(redirectUrl).getResponse();
		} else {
			message = "Bạn đã tải tài liệu này rồi. Vui lòng vào phần quản lý tài liệu của mình kiểm tra lại.";
			return restEntityRes.setHttpStatus(HttpStatus.ACCEPTED).setDataResponse(message).getResponse();
		}
	}
    
	@CrossOrigin(origins = "*")
	@GetMapping("/api/momo/payment")
	public ResponseEntity<?> handlePayment(@RequestParam String orderId, @RequestParam Long amount,  HttpServletResponse httpResponse) throws IOException, InvalidKeyException, NoSuchAlgorithmException {
		UserHistory userHistory = userHistoryService.handlePayment(orderId, amount);
		Optional<User> user = userService.findUserById(userHistory.getUserId());
		if (user.isPresent()) {
			List<UserHistoryFile> historyFiles = userHistoryFileRepository.findByUserHisotyId(userHistory.getId());
			if (!historyFiles.isEmpty()) {
				UserHistoryFile historyFile = historyFiles.get(0);
				Optional<File> fileOptional = fileRepository.findById(historyFile.getFileId());
				if (fileOptional.isPresent()) {
					String message = "Bạn lấy mã code dưới đây nhập vào mục mã file để hoàn tất việc tải file: "+ fileOptional.get().getFileHashcode();
					SimpleMailMessage mailMessage = confirmationTokenService.sendEmailFileHashcode(user.get(), message);
			        emailSenderService.sendEmail(mailMessage);
				}
			}
			
		}
		
		String redirectUrl = "http://ip.pada.me:10003/home/information/download/confirm";
		httpResponse.sendRedirect(redirectUrl);
		String message = "Bạn hãy kiểm tra email để lẫy code download file!";
		return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(message).getResponse();
	}

	private boolean validateAmount(Long amount) {
		if (amount % 1000 == 0) {
			return true;
		}
		return false;
	}
}
