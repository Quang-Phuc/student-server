package com.phuclq.student.common;

import java.util.List;
import java.util.Locale;

/**
 * Class định nghĩa các hằng số
 *
 * @author khanhnt
 * @since 1/12/2021
 */
public class Constants {

  public static final String MESS_COMPLETE_SUCCESS = "Complete tasks success!";
  public static final String TYPE = "type";

  public static final String ID = "id";
  public static final String PROCESS_ID = "processId";
  public static final String FORM_CONFIG = "FORM_CONFIG";
  public static final String INITIAL_VALUE = "INITIAL_VALUE";
  public static final String SUBMIT_VALUE = "SUBMIT_VALUE";
  public static final String DRAFT = "DRAFT";
  public static final String APPROVED = "APPROVED";
  public static final String GATE_WAY = "GATE_WAY";
  public static final String ABSENCE_ID = "ABSENCE_ID";
  public static final String PAYLOAD = "PAYLOAD";
  public static final String APPROVER = "APPROVER";
  public static final String APPROVE_COUNT = "APPROVE_COUNT";
  public static final String LEAVE_OF_ABSENCE = "LEAVE_OF_ABSENCE";
  public static final String CREATED_USER = "CREATED_USER";
  public static final String PROCESS_NAME = "PROCESS_NAME";

  public static final int ACTIVE = 1;
  public static final String CODE_SUCCESS = "200";
  public static final String MESSAGE_SUCCESS = "Success";
  public static final String CODE_ERROR = "400";
  public static final String MESSAGE_ERROR = "Error";
  public static final String COMMON_CATEGORY_COLUMN_CODE = "code";
  public static final String COMMON_CATEGORY_COLUMN_NAME = "name";
  public static final String COMMON_COLUMN_COMMON_CATEGORY_CODE = "commonCategoryCode";
  public static final String ABILITY_CODE = "code";
  public static final String ABILITY_NAME = "name";
  public static final String ABILITY_ID = "id";
  public static final String ABILITY_CAPACITY_GROUP_CODE = "capacityGroupCode";
  public static final String ABILITY_CLASSIFY_CODE = "classifyCode";
  public static final String CODE = "code";
  public static final String NAME = "name";
  public static final String IS_DOMESTIC = "isDomestic";
  public static final String STATUS = "status";
  public static final String START_DATE = "startDate";
  public static final String END_DATE = "endDate";
  public static final String NOTE = "note";
  public static final String QUICK_SEARCH = "quickSearch";

  public static final String ABBREVIATION = "abbreviation";

  public static final String COEFFICIENT = "coefficient";
  public static final String REGISTRATION_NUMBER = "registrationNumber";
  public static final String REGISTRATION_DATE = "registrationDate";
  public static final String CERTIFICATION_DATE = "certificationDate";
  public static final String ASSIGN_DATE = "assignDate";
  public static final String VN_NAME = "vnName";
  public static final String EN_NAME = "enName";
  public static final String ADDRESS = "address";
  public static final String WORK_LOCATION = "workLocation";
  public static final String LOCK_DATE_CONFIG = "LOCK_DATE_CONFIG";

  public static final String WORK_LOCATION_CODE = "workLocationCode";

  public static final String FULL_PATH_RULE = "src/main/resources/rules/";
  public static final String PATH_RULE = "rules/";

  public static final String GENERAL_REQUEST_COLUMN_CHANGE_REQUEST_ID = "id";
  public static final String GENERAL_REQUEST_COLUMN_NAME = "changeRequestName";
  public static final String GENERAL_REQUEST_COLUMN_SIGNING_DATE = "signingDate";
  public static final String GENERAL_REQUEST_COLUMN_CODE = "changeRequestCode";
  public static final String GENERAL_REQUEST_COLUMN_PIC = "pic";
  public static final String GENERAL_REQUEST_COLUMN_SUBMIT_TYPE = "submitType";
  public static final String GENERAL_REQUEST_COLUMN_SUBMIT_CHANNEL = "submitChannel";
  public static final String GENERAL_REQUEST_COLUMN_STATUS = "status";
  public static final String GENERAL_REQUEST_COLUMN_ID = "id";
  public static final String CHANGE_REQUEST_COLUMN_ID = "ticket_id";
  public static final String COUNT = "count";
  public static final String GENERAL_REQUEST_COLUMN_REQUEST_TYPE_ID = "";
  public static final String CREATED_DATE = "createdDate";
  public static final String REQUEST_TYPE_COLUMN_DEPT = "deptCode";
  public static final String REQUEST_TYPE = "requestType";
  public static final String GENERAL_REQUEST_COLUMN_REQUEST_CODE = "requestCode";
  public static final String CREATED_BY = "createdBy";
  public static final String REQUEST_HISTORY_DESCRIPTION = "descriptionRequestItem";
  public static final String ZERO = "0";
  public static final String GENERAL_REQUEST = "generalRequest";
  public static final String REQUEST_HISTORY_GENERAL_ID = "generalItemId";
  public static final String GENERAL_REQUEST_COLUMN_PIC_JOIN = "pic";
  public static final String POLICY_NUMBER = "policyNumber";
  public static final String GROUP_ADMIN = "ADMIN";
  public static final String GROUP_NB_EMPLOYEE = "NB_EMPLOYEE";
  public static final String GROUP_SALE = "SALE";
  public static final String GROUP_NB_ADMIN = "NB_ADMIN";
  public static final String GROUP_NB_APPROVE = "NB_APPROVE";
  public static final String CREATE_REQUEST = "Create request !";
  public static final String MINUS = "-";
  public static final String COMMA = ",";
  public static final String SLASHES = "/";
  public static final String REQUIRE_FILE_TYPE = "ZSAP_CA";
  public static final String IMAGE = "image";
  public static final String PDF = "PDF";
  public static final String REGEX_POLICY_NUMBER_17_CHAR = "^(0){5}[0-9]{12}$";
  public static final String REGEX_POLICY_NUMBER_12_CHAR = "^[0-9]{12}$";
  public static final String REGEX_GP_NUMBER = "^[0-9]{10}$";
  public static final String POLICY_PREFIX = "00000";
  public static final String CLIENT_MESSAGE_ID = "clientMessageId";
  public static final String TRANSACTION_ID = "transactionId";
  public static final String HEADER_KEY_MSG_ID = "msgId";
  public static final String HEADER_KEY_CRE_DT_TM = "creDtTm";
  public static final String HEADER_KEY_FR_SYSTM = "frSystm";
  public static final String DOT_DOT = ":";
  public static final String SHA256_WITH_RSA = "SHA256withRSA";
  public static final String RSA = "RSA";
  public static final String HTTP_DOT_AGENT = "http.agent";
  public static final String CHROME = "Chrome";
  public static final String REVERSED = "Reversed";
  public static final String APPLICATION_PDF = "application/pdf";
  public static final String WEBP = "webp";
  public static final String LAST_UPDATED_DATE = "lastUpdatedDate";
  public static final String ZMSG_EAPP = "ZMSG_EAPP";
  public static final String Z1 = "Z1";
  public static final String ACTIVE_STRING = "Active";
  public static final String HTTPSTATUS_OK_VALUE = "200";
  public static final String S = "S";
  public static final String ACCOUNT = "ACCOUNT";
  public static final String DN = "Dn";
  public static final String DOANH_NGHIEP = "DOANH_NGHIEP";
  public static final String INHOUSE = "INHOUSE";
  public static final String CHI_HO = "CHI_HO";
  public static final String DEFAULT_DEBIT_NAME = "MB Ageas Life Test";
  public static final String DEFAULT_DEBIT_RESOURCE_NUMBER = "0000246003709";
  public static final String DEFAULT_REMARK = "CHUYEN TRA TIEN SO HOP DONG %s";
  public static final String IN_APPROVE = "In approve";
  public static final String APPROVE = "Approve";
  public static final String PAYMENT_METHOD_CASH = "A";
  public static final String DEFAULT_OP_TXT = "DANh DDD";
  public static final String BANKING = "Chuyển khoản";
  public static final String DOT_HTML = ".html";
  public static final String CHECKED = "checked";
  public static final String STRING_FORMAT_2_VARIABLE = "%s %s";
  public static final String DOT = ".";
  public static final String DEFAULT_SAP_OBJECT = "/PM0/ABPLC";
  public static final String PENDING = "Pending";
  public static final String ACTION_AT_DEFAULT_VALUE = "00000000 000000";
  public static final String DEFAULT_BK_DETAILS = "0001";
  public static final String DEFAULT_MERGED_ATTACHMENT_TO_SAP = "Free-look cancellation & Refund";
  public static final String DOT_COMMA = ";";
  public static final String SIMPLE_FORMATTER_DATE_FORMAT = "yyyy-MM-dd";
  public static final String DEFAULT_BP_NUMBER_WHEN_PAYMENT_METHOD_IS_CASH_TO_PASS_SAP =
      "0100124617";
  public static final String VN_MONEY_SURFIX = " đồng";
  public static final String DIVISION = "/";
  public static final String DASH = "-";
  public static final String REFUND_REQ_DECISION_APPROVE = "Approved";
  public static final String REFUND_REQ_DECISION_APPROVE_VN = "Đồng ý";
  public static final String REFUND_REQ_DECISION_REJECTED = "Rejected";
  public static final String REFUND_REQ_DECISION_REJECTED_VN = "Từ chối";
  public static final String REFUND_REQ_REASON_3 = "Freelook Cancellation & Refund";
  public static final String REFUND_REQ_REASON_1 = "Withdrawal & Refund by customer";
  public static final String REFUND_REQ_REASON_VN_3 =
      "hủy hợp đồng bảo hiểm trong 21 ngày và hoàn phí";
  public static final String REFUND_REQ_REASON_VN_1 = "hồ sơ yêu cầu bảo hiểm và hoàn phí";
  public static final String DEFAULT_DATE_FORMAT = "yyyy/MM/dd";
  public static final String VN_DATE_FORMAT = "dd/MM/yyyy";
  public static final String DATETIME_FORMAT_WITHOUT_SEPARATOR = "yyyyMMdd HHmmss";
  public static final String VN_DATETIME_FORMAT_WITH_SEPARATOR = "HH:mm:ss dd/MM/yyyy";
  public static final String POST_METHOD = "POST";
  public static final String GET_METHOD = "GET";
  public static final String PUT_METHOD = "PUT";
  public static final String IN_PROCESS_LATE = "IN_PROCESS_LATE";
  public static final String IN_PROCESS_REMAINING = "IN_PROCESS_REMAINING";
  public static final String DONE_LATE = "DONE_LATE";
  public static final String DONE_IN_TIME = "DONE_IN_TIME";
  public static final String GRANT_TYPE = "client_credentials";
  public static final String FR_SYSTM = "DIGITAL";
  public static final String VND_SYMBOL = "₫";
  public static final String SLA_IS = "[SLa is ] : ";
  public static final String START_BASE64_STRING = "data:%s;base64";
  public static final String UPDATED_PENDING_SALE = "Đã nộp bổ sung ";
  public static final String OVERDUE_SLA_UPDATED_PENDING_SALE = "Quá hạn bổ sung ";
  public static final String REQUEST_ID = "requestId";
  public static final String BPM = "BPM";
  public static final String SAP = "SAP";
  public static final String MBBANK = "MBBANK";
  public static final String MAIL_DOMAIN = "MAIL";
  public static final String POLICY_NO = "POLICYNR_TT";
  public static final String PAGNO_ID = "PAGNO_ID";
  public static final String NOTIFIER = "NOTIFICATION";
  public static final String STRING_FORMAT_2_VARIABLE_WITH_UNDERLINED = "%s_%s";
  public static final String STRING_FORMAT_2_VARIABLE_WITH_TWO_DOT = "%s: %s";
  public static final String STRING_FORMAT_3_VARIABLE_WITH_TWO_DOT = "%s: %s %s";
  public static final String STRING_FORMAT_3_VARIABLE = "%s %s %s";
  public static final String VND = "VND";
  public static final String REPORT = "report";
  public static final String SAP_MESSAGE_SAY = "SAP_MESSAGE_SAY";
  public static final String IN_PROCESS = "In Process";
  public static final String REFUSED = "Refused";
  public static final String APPLICATION_PREFIX_3 = "000";
  public static final String APPLICATION_PREFIX_8 = "00000000";
  public static final String PAYMENT_METHOD_BANKING = "I";
  public static final String REJECT = "REJECT";
  public static final String TYPE_X = "X";
  public static final String REGEX_APPLICATION_NUMBER_20_CHAR = "^(0){8}[0-9]{12}$";
  public static final String UPDATE_REQUEST_DETAIL_DTO = "updateRequestDetailDTO";
  public static final String IS_UPDATE = "isUpdate";
  public static final String NBU_USER_DECISION = "nbuUserDecision";
  public static final String MEDICAL_FEE = "medicalFee";
  public static final String REASON = "reason";
  public static final String SUB_REASON = "subReason";
  public static final String HAVE_CANCELLATION_REQUEST_FORM = "haveCancellationRequestForm";
  public static final String APPROVE_PAYMENT_REQUEST_DTO = "approvePaymentRequestDTO";
  public static final String IS_PROCESSING = "isProcessing";
  public static final String NBU_FILTER_PAYMENT_LOT_STATUS = "Free";
  public static final String PROC_INST_VAR_IS_PAYMENT_LOT_UPDATED = "isPaymentLotItemsUpdated";
  public static final String PROC_INST_VAR_NBU_DECISION = "nbuDecision";
  public static final String SAP_SERVICE_FEIGN = "SapServiceFeign";
  public static final String REQUEST_BACKEND = "Request-BackEnd";
  public static final String LOCAL_DATE_TIME_FORMAT = "yyyy-MM-dd hh:mm:ss";
  public static final String REPAYMENT_REQUEST_OPTION_EQ = "EQ";
  public static final String REPAYMENT_REQUEST_OPTION_BT = "BT";
  public static final String PROC_INST_VAR_IS_REPAYMENT_REQUEST = "isRepaymentRequest";
  public static final String PROC_INST_VAR_IS_PAYMENT_REQUEST = "isPaymentRequest";
  public static final String EQ = "EQ";
  public static final String REQUIRED = "required";
  public static final String XXDEFAULT = "XXDEFAULT";
  public static final String NBU_MEMBER_ROLE_CODE = "NBU_MEMBER";
  public static final String P2P_APPROVE_STATUS = "p2pApproveStatus";
  public static final String P2P_APPROVE_DECISION = "APPROVED";
  public static final String P2P_REJECT_DECISION = "REJECT";
  public static final String P2P_IN_APPROVE = "IN APPROVE";
  public static final String PROC_INST_VAR_MAIL_TEMPLATE = "mailTemplate";
  public static final String CASH = "Cash";
  public static final String BANK = "Banking";
  public static final String VN = "VN";
  public static final String PROC_INST_VAR_IS_PROCESSED = "isProcessed";
  public static final String OP = "OP";
  public static final String OP_NBU = "OP_NBU";
  public static final String OP_NBU_USER = "OP_NBU_USER";
  public static final String NBU = "NBU";
  public static final String PROC_INST_VAR_IC_EMAIL_ADDRESS = "custEmail";
  public static final String PROC_INST_VAR_IC_AGENT_NAME = "agentName";
  public static final String SUBREASONAPPROVE = "SUBREASONAPPROVE";
  public static final String PENDING_REASON = "PENDING_REASON";

  public static final String SEND_TO_BCC = "SEND_TO_BCC";
  public static final String REJECT_REASON = "REJECT_REASON";
  public static final String SAP_NOT_FOUND_APP_NUMBER = "Application don't exist";
  public static final String STRING_FORMAT_2_VARIABLE_WITH_SLASH = "%s / %s";
  public static final String P2P_APPROVE_DECISION_VN = "Phê duyệt yêu cầu";
  public static final String P2P_REJECT_DECISION_VN = "Từ chối yêu cầu";
  public static final String REFUND_REQ_DECISION_PENDING = "Pending";
  public static final String REFUND_REQ_DECISION_PENDING_VN = "Yêu cầu bổ sung";
  public static final String CREATED_APP_UW_SIGNAL_NAME = "nbu-app-uw-create";
  public static final String IC_MAIN = "X";
  public static final String PROC_INST_VAR_APP_UW_PROCESS_OPTION = "processOption";
  public static final String PROC_INST_VAR_APP_UW_NBU_DECISION_APPROVE = "2";
  public static final String P2P_APPROVE_COMMON_CODE = "2";
  public static final String P2P_REJECT_COMMON_CODE = "3";
  public static final String PROC_INST_VAR_APP_UW_NBU_DECISION_REJECT = "1";
  public static final String PROC_INST_VAR_APP_UW_NBU_DECISION_PENDING = "0";
  public static final String PROC_INST_VAR_IS_APP_REFUSED = "isApplicationRefuse";
  public static final String APPROVEDREASON = "APPROVEDREASON";
  public static final String APP_UW_PROCESS_OPTION_TRANSFER = "2";
  public static final String APP_UW_PROCESS_OPTION_REFUND = "3";
  public static final String NBU_APP_UW_REQ_PROCESS_OPTION = "NBU_APP_UW_REQ_PROCESS_OPTION";
  public static final String I = "I";
  public static final String REFUND_CASH_BANK_ACCOUNT = "REFUND_CASH_BANK_ACCOUNT";
  public static final String CASH_KEY_QAS = "CASH_KEY_QAS";
  public static final String CASH_ACCOUNT_QAS = "CASH_ACCOUNT_QAS";
  public static final String CASH_BP_NUMBER = "CASH_BP_NUMBER";
  public static final String SAP_FILE_TYPE_NBU = "SAP_FILE_TYPE_NBU";
  public static final String REQUEST_HISTORY_APP_UW_SP_DECISION = "SP gửi quyết định";
  public static final String NBU_APP_REFUSAL_SUB_REASON = "NBU_APP_REFUSAL_SUB_REASON";
  public static final String NBU_APP_REFUSAL_REASON = "NBU_APP_REFUSAL_REASON";
  public static final String ZSAP_CA_FILENAME = "71_CA_Policy cancellation";
  public static final String Z1PDF = "Z1PDF";
  public static final String ZTATT = "ZTATT";
  public static final String PROC_INST_VAR_REQUEST_ID = "requestId";
  public static final String APP_REFUND_APPROVE = "APP_REFUND_APPROVE";
  public static final String APP_REFUND_PENDING = "APP_REFUND_PENDING";
  public static final String APP_REFUND_REJECT = "APP_REFUND_REJECT";
  public static final String APP_TRANSFER_APPROVE = "APP_TRANSFER_APPROVE";
  public static final String APP_TRANSFER_PENDING = "APP_TRANSFER_PENDING";
  public static final String APP_TRANSFER_REJECT = "APP_TRANSFER_REJECT";
  public static final String APP_UW_APPROVE = "APP_UW_APPROVE";
  public static final String APP_UW_PENDING = "APP_UW_PENDING";
  public static final String APP_UW_REJECT = "APP_UW_REJECT";
  public static final String REQUEST_HISTORY_DESCRIPTION_PENDING = "Yêu cầu bổ sung lần %s";
  public static final String DATERUN = "DATERUN";
  public static final String PROC_INST_VAR_APP_UW_POLICY_NUM_LIST = "policyNumberList";
  public static final String PROC_INST_VAR_TIME_WAITED = "timeWaited";
  public static final String PENDING_APP_SCAN_CONFIG = "PENDING_APP_SCAN_CONFIG";
  public static final String REFUND_APPROVE = "REFUND_APPROVE";
  public static final String REFUND_REJECT = "REFUND_REJECT";
  public static final String REFUND_PENDING = "REFUND_PENDING";

  public static final String BPM_EMAIL_CONFIG = "BPM_EMAIL_CONFIG";
  public static final String PROC_INST_VAR_APP_NO_REFUND = "noRefund";
  public static final String PROC_INST_VAR_PENDING_DURATION = "pendingDuration";
  public static final String PROC_INST_VAR_SALE_DECISION_PENDING_DURATION =
      "saleDecisionPendingDuration";
  public static final String PROC_INST_DURATION_VAR_BUILDER_P = "P";
  public static final String PROC_INST_DURATION_VAR_BUILDER_D = "D";
  public static final String PROC_INST_DURATION_VAR_BUILDER_T = "T";
  public static final String PROC_INST_DURATION_VAR_BUILDER_H = "H";
  public static final String PROC_INST_DURATION_VAR_BUILDER_M = "M";
  public static final String PROC_INST_VAR_CANCEL_PENDING = "cancelPending";
  public static final String APP_SALE_PENDING_T10 = "APP_SALE_PENDING_T10";
  public static final String APP_SALE_PENDING_T16 = "APP_SALE_PENDING_T16";
  public static final String PROC_INST_VAR_APP_UW_SCAN_POLICYNUM_SUB_LIST = "policyNumberSubList";
  public static final String APPROVER_ACTION_ONE = "1";
  public static final String APPROVER_ACTION_TWO = "2";
  public static final String SAP_MSG_APP_NOT_EXIST = "App do not exist";
  public static final String SAP_MSG_APPLICATION_NOT_EXIST = "Application don't exist";
  public static final String PROC_INST_VAR_APP_UW_SCAN_DUE_DATE_APP_LIST_IS_EMPTY =
      "dueDateAppListIsEmpty";
  public static final String COMMON_APPROVE_CODE = "Request's approving status code";
  public static final String COMMON_APPROVES_STEP_CODE = "Request's approvers status code";
  public static final String APP_UW_POSTPONE_REASON = "903";
  public static final String REASON_004 = "004";
  public static final String REASON_011 = "011";
  public static final String NOT_AVAILABLE = "N/A";
  public static final String REQUEST_TYPE_COMMON = "REQUEST_TYPE";
  public static final String REASON_IN_VN = "\n Lí do :";
  public static final String APPLICATION_TRANSFER_REFUSAL_REASON = "98";
  public static final String APPLICATION_REFUND_REFUSAL_REASON = "99";
  public static final String PAYFRQ_CD = "PAYFRQ_CD";
  public static final String PAYMENT_NOTE = "PENDING_NOTE";
  public static final String DATE_FORMAT_DD_MM_YYYY = "dd-MM-yyyy";

  // cho phép kí tự số trong tên BP
  public static final String REFUND_REQUEST_NAME_REGEX =
      "^[a-zA-Z0-9_ÀÁÂÃÈÉÊẾÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêếìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\ ]+$";
  public static final String REFUND_REQUEST_ID_REGEX_1 = "^([0-9]{9}|[0-9]{12})$";
  public static final String REFUND_REQUEST_NAME_REGEX_NO_SPECIAL =
      "[^~`!@#$%^&*()_+={][}|:;<,>.?/]";
  public static final String REFUND_REQUEST_ID_REGEX_2 = "^[A-Za-z][0-9]{7}$";
  public static final String REFUND_REQUEST_ID_REGEX_3 = "^[A-Za-z0-9]{3}[0-9]{4,8}$|^[0-9]{12}$";


  public static final String UP_COMMA = "'";
  public static final String CREATE_BP_SUCCESS_MESSAGE = "S";
  public static final String CREATE_BANK_DETAIL_FAIL = "W";
  public static final String START_OF_ULK_POLICY_NUMBER_NUM_21 = "21";
  public static final String SEND_TO_SAP = "SEND_TO_SAP";
  public static final String BEING_PROCESSED_BY = "being processed by";
  public static final String APPROVE_STATUS = "approveStatus";
  public static final String HISTORY_TYPE = "historyType";
  public static final String AUTHOR_BP_REFUND_DEFAULT_PAYMENT_NOTE =
      "Chủ hợp đồng %s - %s uỷ quyền nhận tiền";
  public static final String STRING_FORMAT_4_VARIABLE_WITH_TWO_DOT = "%s %s: %s %s";
  public static final String SAP_IDENTIFICATION_TYPE_CODE = "SAP_IDENTIFICATION_TYPE_CODE";
  public static final String MB_BANK_NAME = "Ngân hàng quân đội Mb";
  public static final String MB_BANK_UER_NAME = "MBAL";
  public static final String DEFAULT_APP_PAYMENT_NOTE_FOR_AUTHORIZED_REQ =
      "Chủ HSYCBH %s - %s uỷ quyền nhận tiền";
  public static final String APP_REFUND_WITH_MEDICAL_FEE =
      "Hủy HSYCBH và hoàn phí BH - Có trừ phí khám";
  public static final String APP_UW_REFUND_WITH_MEDICAL_FEE =
      "Hủy HSYCBH theo quyết định của phòng thẩm định - Hoàn phí có trừ phí khám";

  public static final String POLICY_NUMBER_PAYMENT_REQUEST_CASH_METHOD_DESCIPTION_WITH_MEDICAL_FEE =
      "Hoàn phí bảo hiểm cho Khách hàng/Refund - Có trừ phí khám";
  public static final String POLICY_NUMBER_PAYMENT_REQUEST_CASH_METHOD_DESCIPTION =
      "Hoàn phí bảo hiểm cho Khách hàng/Refund";
  public static final String PROC_INST_VAR_SAVED_DECISION_ID = "savedDecisionId";
  public static final String ASSIGNMENT_MODE = "ASSIGNMENT_MODE";
  public static final String ASSIGNMENT_MODE_EQUALLY_DIVIDED = "EQUALLY_DIVIDED";
  public static final String ASSIGNMENT_MODE_TARGET_USER = "TARGET_USER";
  public static final String TARGET_USER = "TARGET_USER";
  public static final String P2P_APPROVE_REFUND_TASK_NAME = "P2P_APPROVE";
  public static final String FAIL_TO_REFUSAL_DUE_TO_CBC_APPL = "Call of GET_BO with IV_BO_ID = 0";
  public static final String LAPSE = "LAPSE";
  public static final String DORMANT = "DORMANT";
  public static final String PAYMENT_LOT_FREE_STATUS = "01";
  public static final String LOG_ERROR_FORMAT = "[%s]-[Request body : %s ]-[Response body : %s] ";
  public static final String GENDER = "GENDER";
  public static final String Z = "Z";
  public static final String MALE = "MALE";
  public static final String MALE_VN = "Nam";
  public static final String FEMALE = "FEMALE";
  public static final String FEMALE_VN = "Nữ";
  public static final String HOLIDAYS_TYPE_CODE = "HOLIDAYS";
  public static final String MORNING_START_TIME = "MORNING_START_TIME";
  public static final String LUNCH_START_TIME = "LUNCH_START_TIME";
  public static final String LUNCH_END_TIME = "LUNCH_END_TIME";
  public static final String AFTERNOON_END_TIME = "AFTERNOON_END_TIME";
  public static final String WORK_TIME_DAILY = "WORK_TIME_DAILY";

  private Constants() {
    throw new IllegalStateException("Constant class");
  }
}
