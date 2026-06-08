package com.newgen.iforms.user;

import com.newgen.iforms.custom.IFormReference;
import com.newgen.mvcbeans.model.wfobjects.WDGeneralData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;

public class CSR_RR_Email extends CSR_RRCommon
{
  public static String mailTrigger(IFormReference iform, String stage, String data)
  {
    try
    {
      String rejectReason;
      int size;
      JSONArray str;
      String OthersrejectReason;
      int i;
      String reason;
      CSR_RR.mLogger.debug("Inside mailTrigger method-----------> ");
      CSR_RR.mLogger.debug("data-----------> " + data);
      String[] param = data.split("-");

      String tag = "";
      String WI_No = (String)iform.getValue("wi_name");
      CSR_RR.mLogger.debug("WI_No----------->" + WI_No);
      String split_WI_No = splitString(WI_No);
      CSR_RR.mLogger.debug("split_WI_No----------->" + split_WI_No);
      String Card_No = param[0];
      CSR_RR.mLogger.debug("Card_No----------->" + Card_No);
      String lastDigitCard_No = Card_No.substring(12, 16);
      CSR_RR.mLogger.debug("lastDigitCard_No----------->" + lastDigitCard_No);

      String getCustMail = "Select userEmailID FROM RB_CSR_RR_EXTTABLE WHERE WI_NAME = '" + WI_No + "'";
      List Query_data1 = iform.getDataFromDB(getCustMail);
      String CustMail = (String)((List)Query_data1.get(0)).get(0);
      CSR_RR.mLogger.debug("CustMail----------->" + CustMail);
      String reversalFor = (String)iform.getValue("RRD_RFC");
      CSR_RR.mLogger.debug("reversalFor----------->" + reversalFor);
      if (iform.getActivityName().equalsIgnoreCase("CARDS")) {
        rejectReason = (String)iform.getValue("Cards_Reject");
        CSR_RR.mLogger.debug("rejectReason--------->" + rejectReason);
        tag = "<p class=MsoListParagraphCxSpFirst align=left style=\"margin-bottom:0in; text-align:left;padding-left:31%;text-indent:-.25in;line-height:normal\"><span style=\"font-size:10.0pt;font-family:Symbol\">*<span style=\"font:7.0pt \"Times New Roman\"\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></span><span style=\"font-size:10.0pt;font-family:\"Verdana\",sans-serif\">" + 
          rejectReason + "</span></p>" + "\n";
        size = 0;
        str = new JSONArray();
        if (rejectReason.equalsIgnoreCase("Others")) {
          OthersrejectReason = (String)iform.getValue("Others_Reject_Reason");
          CSR_RR.mLogger.debug("OthersrejectReason----------->" + OthersrejectReason);
          tag = "<p class=MsoListParagraphCxSpFirst align=left style=\"margin-bottom:0in;padding-left:31%; text-align:left;text-indent:-.25in;line-height:normal\"><span style=\"font-size:10.0pt;font-family:Symbol\">*<span style=\"font:7.0pt \"Times New Roman\"\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></span><span style=\"font-size:10.0pt;font-family:\"Verdana\",sans-serif\">" + 
            OthersrejectReason + "</span></p>" + "\n";
        } else if (rejectReason.equalsIgnoreCase("Due to non receipt of")) {
          str = (JSONArray)iform.getValue("USR_0_CSR_RR_REJECT_SUB_REASON");
          size = str.size();
          tag = "";
          for (i = 0; i < size; ++i) {
            reason = (String)str.get(i);
            tag = tag + 
              "<p class=MsoListParagraphCxSpFirst align=left style=\"margin-bottom:0in; text-align:left;padding-left:31%;text-indent:-.25in;line-height:normal\"><span style=\"font-size:10.0pt;font-family:Symbol\">*<span style=\"font:7.0pt \"Times New Roman\"\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></span><span style=\"font-size:10.0pt;font-family:\"Verdana\",sans-serif\">" + 
              reason + "</span></p>" + "\n";
          }
        }
      } else if (iform.getActivityName().equalsIgnoreCase("Pending")) {
        rejectReason = (String)iform.getValue("Pending_Reason");
        CSR_RR.mLogger.debug("rejectReason--------->" + rejectReason);
        tag = "<p class=MsoListParagraphCxSpFirst align=left style=\"margin-bottom:0in; text-left:padding-left:31%;center;text-indent:-.25in;line-height:normal\"><span style=\"font-size:10.0pt;font-family:Symbol\">*<span style=\"font:7.0pt \"Times New Roman\"\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></span><span style=\"font-size:10.0pt;font-family:\"Verdana\",sans-serif\">" + 
          rejectReason + "</span></p>" + "\n";
        size = 0;
        str = new JSONArray();
        if (rejectReason.equalsIgnoreCase("Others")) {
          i = (String)iform.getValue("Others_Pending_SubReason");
          CSR_RR.mLogger.debug("OthersrejectReason----------->" + i);
          tag = "<p class=MsoListParagraphCxSpFirst align=left style=\"margin-bottom:0in; text-align:left;padding-left:31%;text-indent:-.25in;line-height:normal\"><span style=\"font-size:10.0pt;font-family:Symbol\">*<span style=\"font:7.0pt \"Times New Roman\"\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></span><span style=\"font-size:10.0pt;font-family:\"Verdana\",sans-serif\">" + 
            i + "</span></p>" + "\n";
        } else if (rejectReason.equalsIgnoreCase("Due to non receipt of")) {
          str = (JSONArray)iform.getValue("USR_0_CSR_RR_PENDING_SUB");
          size = str.size();
          tag = "";
          for (i = 0; i < size; ++i) {
            reason = (String)str.get(i);
            tag = tag + 
              "<p class=MsoListParagraphCxSpFirst align=left style=\"margin-bottom:0in; text-align:left;padding-left:31%;text-indent:-.25in;line-height:normal\"><span style=\"font-size:10.0pt;font-family:Symbol\">*<span style=\"font:7.0pt \"Times New Roman\"\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></span><span style=\"font-size:10.0pt;font-family:\"Verdana\",sans-serif\">" + 
              reason + "</span></p>" + "\n";
          }
        }
      } else if (iform.getActivityName().equalsIgnoreCase("Branch_Approver")) {
        rejectReason = (String)iform.getValue("BA_Reject");
        CSR_RR.mLogger.debug("rejectReason--------->" + rejectReason);
        tag = "<p class=MsoListParagraphCxSpFirst align=left style=\"margin-bottom:0in;padding-left:31%; text-align:left;text-indent:-.25in;line-height:normal;padding-left:40%\"><span style=\"font-size:10.0pt;font-family:Symbol\">*<span style=\"font:7.0pt \"Times New Roman\"\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></span><span style=\"font-size:10.0pt;font-family:\"Verdana\",sans-serif\">" + 
          rejectReason + "</span></p>" + "\n";
        size = 0;
        str = new JSONArray();
        if (rejectReason.equalsIgnoreCase("Others")) {
          i = (String)iform.getValue("Others_BA");
          CSR_RR.mLogger.debug("OthersrejectReason----------->" + i);
          tag = "<p class=MsoListParagraphCxSpFirst align=left style=\"margin-bottom:0in; text-align:left;padding-left:31%;text-indent:-.25in;line-height:normal\"><span style=\"font-size:10.0pt;font-family:Symbol\">*<span style=\"font:7.0pt \"Times New Roman\"\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></span><span style=\"font-size:10.0pt;font-family:\"Verdana\",sans-serif\">" + 
            i + "</span></p>" + "\n";
        } else if (rejectReason.equalsIgnoreCase("Due to non receipt of")) {
          str = (JSONArray)iform.getValue("USR_0_CSR_RR_BA_SUB_REASON");
          size = str.size();
          tag = "";
          for (i = 0; i < size; ++i) {
            reason = (String)str.get(i);
            tag = tag + 
              "<p class=MsoListParagraphCxSpFirst align=left style=\"margin-bottom:0in;padding-left:31%; text-align:left;text-indent:-.25in;line-height:normal\"><span style=\"font-size:10.0pt;font-family:Symbol\">*<span style=\"font:7.0pt \"Times New Roman\"\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></span><span style=\"font-size:10.0pt;font-family:\"Verdana\",sans-serif\">" + 
              reason + "</span></p>" + "\n";
          }

        }

      }

      String Query = "Select * From USR_0_CSR_BT_TemplateMapping where ProcessName = 'CSR_RR' and TemplateType = '" + 
        stage + "'";
      List Query_data = iform.getDataFromDB(Query);
      CSR_RR.mLogger.debug("Query_data----------->" + Query_data);
      if (Query_data.size() <= 0) break label1764;
      String emailBody = (String)((List)Query_data.get(0)).get(2);
      CSR_RR.mLogger.debug("emailBody before replace" + emailBody);
      if (emailBody.equalsIgnoreCase("NULL")) break label1764;
      emailBody = emailBody.replaceAll("#WI_No#", split_WI_No);
      emailBody = emailBody.replaceAll("#Card_No#", lastDigitCard_No);
      emailBody = emailBody.replaceAll("'Times", "''Times");
      emailBody = emailBody.replaceAll("Roman'", "Roman''");
      emailBody = emailBody.replaceAll("#reject reason#", tag);
      if (!(reversalFor.equalsIgnoreCase("Others Pls. Specify")))
        emailBody = emailBody.replaceAll("#Reversal_For#", reversalFor);
      else {
        emailBody = emailBody.replaceAll("#Reversal_For#", "");
      }
      CSR_RR.mLogger.debug("emailBody after replace" + emailBody);

      String mailFrom = (String)((List)Query_data.get(0)).get(3);
      CSR_RR.mLogger.debug("mailFrom----->" + mailFrom);
      String mailTo = CustMail;
      String mailSubject = (String)((List)Query_data.get(0)).get(6);
      CSR_RR.mLogger.debug("Mail Subject------>" + mailSubject);
      String mailContentType = "text/html;charset=UTF-8";
      int mailPriority = 1;
      int workitemId = 1;
      int noOfTrials = 1;
      int activityId = 3;
      String mailStatus = "N";
      String mailActionType = "TRIGGER";
      String tableName = "WFMAILQUEUETABLE";
      String columnName = "(mailFrom,mailTo,mailSubject,mailMessage, mailContentType, mailPriority,mailStatus,insertedBy,mailActionType,processInstanceId,workitemId,activityId,noOfTrials)";

      String values = "('" + mailFrom + "','" + mailTo + "','" + mailSubject + "',N'" + emailBody + "','" + 
        mailContentType + "','" + mailPriority + "','" + mailStatus + "','" + getUserName() + 
        "','" + mailActionType + "','" + iform.getObjGeneralData().getM_strProcessInstanceId() + 
        "','" + workitemId + "','" + activityId + "','" + noOfTrials + "')";
      String mailInsertQuery = "Insert into " + tableName + " " + columnName + " values " + values;
      CSR_RR.mLogger.debug("Query to be inserted in table-----------------: " + mailInsertQuery);
      int status = iform.saveDataInDB(mailInsertQuery);
      CSR_RR.mLogger.debug("Status------------>" + status);
      CSR_RR.mLogger
        .debug("Mail Triggred successfuly if value of status is 1---------STATUS = " + status);
      if (status != 1) break label1764;
      return "true";
    }
    catch (Exception ex)
    {
      CSR_RR.mLogger.debug("Some error in mailTrigger " + ex.toString());
      return "false";
    }
    label1764: return "false";
  }

  public static String sendSMS(IFormReference iform, String stage, String data) {
    try {
      String columnName;
      String SMSInsertQuery;
      CSR_RR.mLogger.debug("inside sendSMScall txtMessagessss");
      CSR_RR.mLogger.debug("data----->" + data);
      String[] param = data.split("-");

      String WI_No = (String)iform.getValue("wi_name");
      CSR_RR.mLogger.debug("WI_No------->" + WI_No);
      String split_WI_No = splitString(WI_No);
      CSR_RR.mLogger.debug("split_WI_No----------->" + split_WI_No);

      String Card_No = param[0];
      CSR_RR.mLogger.debug("Card_No------->" + Card_No);
      String lastDigitCard_No = Card_No.substring(12, 16);
      CSR_RR.mLogger.debug("lastDigitCard_No------->" + lastDigitCard_No);

      String reversalFor = (String)iform.getValue("RRD_RFC");
      CSR_RR.mLogger.debug("reversalFor----------->" + reversalFor);

      String date = getDate();
      String smsLang = "EN";

      String AlertID = "";
      String DynamicTags = "";
      String infobipIsActive = "";
      String CIF = "";

      String QueryExTable = "SELECT CIF FROM RB_CSR_RR_EXTTABLE WHERE wi_name = '" + WI_No + "'";
      CSR_RR.mLogger.debug("CIF DB Query :" + QueryExTable);
      List QueryExTableList = iform.getDataFromDB(QueryExTable);
      if (QueryExTableList.size() > 0) {
        CIF = (String)((List)QueryExTableList.get(0)).get(0);
      }
      CSR_RR.mLogger.debug("Data from DB CIF :" + CIF);

      String Query = "SELECT * FROM USR_0_CSR_BT_TemplateMapping WHERE ProcessName = 'CSR_RR' AND TemplateType = '" + 
        stage + "'";
      List Query_data = iform.getDataFromDB(Query);
      CSR_RR.mLogger.debug("Query_data------->" + Query_data);

      if (Query_data.size() <= 0) break label1677;
      String txtMessage = (String)((List)Query_data.get(0)).get(5);
      infobipIsActive = (String)((List)Query_data.get(0)).get(10);
      AlertID = (String)((List)Query_data.get(0)).get(8);
      DynamicTags = (String)((List)Query_data.get(0)).get(9);

      CSR_RR.mLogger.debug("infobip is Active " + infobipIsActive);
      CSR_RR.mLogger.debug("infobip Alert ID " + AlertID);
      CSR_RR.mLogger.debug("infobip Dynamic Tags " + DynamicTags);

      if ((!(txtMessage.equalsIgnoreCase("NULL"))) && (infobipIsActive.equalsIgnoreCase("N"))) {
        CSR_RR.mLogger.debug("txtMessage before replace" + txtMessage);
        txtMessage = txtMessage.replaceAll("#WI_No#", split_WI_No);
        txtMessage = txtMessage.replaceAll("#Card_No#", lastDigitCard_No);
        txtMessage = txtMessage.replaceAll("#DD/MM/YYYY#", date);
        if (!(reversalFor.equalsIgnoreCase("Others Pls. Specify")))
          txtMessage = txtMessage.replaceAll("#Reversal_For#", reversalFor);
        else {
          txtMessage = txtMessage.replaceAll("#Reversal_For#", "");
        }
        CSR_RR.mLogger.debug("txtMessage after replace" + txtMessage);

        String tableName = "NG_RLOS_SMSQUEUETABLE";
        String ALERT_Name = stage;
        String Alert_Code = "CSR_RR";
        String Alert_Status = "P";
        Mobile_No = param[1];
        CSR_RR.mLogger.debug("Mobile no--------->" + Mobile_No);
        Workstep_Name = iform.getActivityName();

        columnName = "(ALERT_Name, Alert_Code, Alert_Status, Mobile_No, Alert_Text, WI_Name, Workstep_Name, Inserted_Date_time, isViaInfobip)";
        String values = "('" + ALERT_Name + "','" + Alert_Code + "','" + Alert_Status + "','" + Mobile_No + 
          "','" + txtMessage + "','" + WI_No + "','" + Workstep_Name + "', getdate(),'Y')";
        SMSInsertQuery = "INSERT INTO " + tableName + " " + columnName + " VALUES " + values;
        CSR_RR.mLogger.debug("Query to be inserted in table-----------------: " + SMSInsertQuery);

        int status = iform.saveDataInDB(SMSInsertQuery);
        CSR_RR.mLogger
          .debug("SMS Triggered successfully if value of status is 1-------------STATUS = " + status);
        if (status != 1) break label1677;
        return "true";
      }
      if (!(infobipIsActive.equalsIgnoreCase("Y"))) break label1677;
      CSR_RR.mLogger.debug("txtMessage before replace" + txtMessage);
      txtMessage = txtMessage.replaceAll("#WI_No#", split_WI_No);
      txtMessage = txtMessage.replaceAll("#Card_No#", lastDigitCard_No);
      txtMessage = txtMessage.replaceAll("#DD/MM/YYYY#", date);
      if (!(reversalFor.equalsIgnoreCase("Others Pls. Specify")))
        txtMessage = txtMessage.replaceAll("#Reversal_For#", reversalFor);
      else {
        txtMessage = txtMessage.replaceAll("#Reversal_For#", "");
      }
      CSR_RR.mLogger.debug("txtMessage after replace" + txtMessage);
      String DynamicValues = "";
      String[] tags = DynamicTags.split("~");
      CSR_RR.mLogger.debug("Dynamic Tag Arr: " + Arrays.toString(tags));

      List valueList = new ArrayList();
      String Workstep_Name = (columnName = tags).length; for (String Mobile_No = 0; Mobile_No < Workstep_Name; ++Mobile_No) { String tag1 = columnName[Mobile_No];
        String pValue = "";
        switch ((SMSInsertQuery = tag1.trim()).hashCode())
        {
        case -175704520:
          if (SMSInsertQuery.equals("reversal_For"));
          break;
        case 112167566:
          if (SMSInsertQuery.equals("wI_No"));
          break;
        case 553933168:
          if (SMSInsertQuery.equals("card_No"));
          break;
        case 686378048:
          if (!(SMSInsertQuery.equals("dDMMYYYY"))) { break label1320:

            pValue = lastDigitCard_No;
            break label1320:

            pValue = split_WI_No;
          }
          else {
            pValue = date;
            break label1320:

            pValue = (!(reversalFor.equalsIgnoreCase("Others Pls. Specify"))) ? reversalFor : "";
          }
        }
        label1320: valueList.add(pValue);
      }

      DynamicValues = String.join("~#~", valueList);
      CSR_RR.mLogger.debug("Final List of Dynamic Values: " + valueList);

      String tableName = "USR_0_INFOBIP_SMS_QUEUETABLE";
      String ALERT_Name = stage;
      String ProcessName = "CSR_RR";
      String Alert_Status = "P";
      String Mobile_No = param[1];
      CSR_RR.mLogger.debug("Mobile no--------->" + Mobile_No);
      String Workstep_Name = iform.getActivityName();

      String columnName = "(Processname,WI_NAME,AlertID,InsertedDateTime,CIF,Dynamic_Tags,Dynamic_Values,Alert_Status,MobileNumber,SMS_Content)";
      String values = "('" + ProcessName + "','" + WI_No + "','" + AlertID + 
        "',format(getdate(),'yyyy-MM-dd HH:mm:ss.fff'),'" + CIF + "','" + DynamicTags + "','" + 
        DynamicValues + "','" + Alert_Status + "','" + Mobile_No + "','" + txtMessage + "')";
      String SMSInsertQuery = "INSERT INTO " + tableName + " " + columnName + " VALUES " + values;
      CSR_RR.mLogger.debug("Query to be inserted in table-----------------: " + SMSInsertQuery);

      int status = iform.saveDataInDB(SMSInsertQuery);
      CSR_RR.mLogger
        .debug("SMS Triggered successfully if value of status is 1-------------STATUS = " + status);
      if (status != 1) break label1677;
      return "true";
    }
    catch (Exception ex)
    {
      CSR_RR.mLogger.debug("Some error in sendSMScall" + ex.toString());
      return "false";
    }
    label1677: return "false";
  }
}