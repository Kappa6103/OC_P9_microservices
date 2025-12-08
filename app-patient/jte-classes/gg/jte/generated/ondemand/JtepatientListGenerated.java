package gg.jte.generated.ondemand;
import com.medilabo.patient.models.Patient;
import java.util.List;
@SuppressWarnings("unchecked")
public final class JtepatientListGenerated {
	public static final String JTE_NAME = "patientList.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,2,2,2,16,16,16,17,17,17,18,18,19,19,21,21,32,32,34,34,34,35,35,35,36,36,36,37,37,37,38,38,38,39,39,39,41,41,41,41,44,44,44,44,50,50,52,52,61,61,61,2,3,3,3,3};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, List<Patient> patientList, String successMessage) {
		jteOutput.writeContent("\r\n<!doctype html>\r\n<html lang=\"en\">\r\n<head>\r\n    <meta charset=\"UTF-8\">\r\n    <meta name=\"viewport\"\r\n          content=\"width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0\">\r\n    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\r\n    <title>Document</title>\r\n    <link rel=\"stylesheet\" href=\"/css/style.css\">\r\n</head>\r\n<body>\r\n");
		if (successMessage != null && !successMessage.isBlank()) {
			jteOutput.writeContent("\r\n    <p>");
			jteOutput.setContext("p", null);
			jteOutput.writeUserContent(successMessage);
			jteOutput.writeContent("</p>\r\n");
		}
		jteOutput.writeContent("\r\n");
		if (patientList.isEmpty()) {
			jteOutput.writeContent("\r\n    <p>There is no Patients in the system</p>\r\n");
		} else {
			jteOutput.writeContent("\r\n    <table>\r\n        <tr>\r\n            <th>Firstname</th>\r\n            <th>Family name</th>\r\n            <th>Date of birth</th>\r\n            <th>Gender</th>\r\n            <th>address</th>\r\n            <th>Phone number</th>\r\n            <th>Action</th>\r\n        </tr>\r\n        ");
			for (var patient : patientList) {
				jteOutput.writeContent("\r\n            <tr>\r\n                <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(patient.getFirstName());
				jteOutput.writeContent("</td>\r\n                <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(patient.getLastName());
				jteOutput.writeContent("</td>\r\n                <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(patient.getFormattedBirthday());
				jteOutput.writeContent("</td>\r\n                <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(patient.getGender());
				jteOutput.writeContent("</td>\r\n                <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(patient.getAddress());
				jteOutput.writeContent("</td>\r\n                <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(patient.getPhoneNumber());
				jteOutput.writeContent("</td>\r\n                <td>\r\n                    <form action=\"/patient/update/");
				jteOutput.setContext("form", "action");
				jteOutput.writeUserContent(patient.getId());
				jteOutput.setContext("form", null);
				jteOutput.writeContent("\" method=\"get\" style=\"display: inline;\">\r\n                        <button type=\"submit\">Edit</button>\r\n                    </form>\r\n                    <form action=\"/patient/delete/");
				jteOutput.setContext("form", "action");
				jteOutput.writeUserContent(patient.getId());
				jteOutput.setContext("form", null);
				jteOutput.writeContent("\" method=\"get\" style=\"display: inline;\">\r\n                        <button type=\"submit\" class=\"link-button\">Delete</button>\r\n                    </form>\r\n                </td>\r\n\r\n            </tr>\r\n        ");
			}
			jteOutput.writeContent("\r\n    </table>\r\n");
		}
		jteOutput.writeContent("\r\n\r\n<form action=\"/patient/list\" method=\"get\">\r\n    <button type=\"submit\">Refresh List</button>\r\n</form>\r\n<form action=\"/patient/add\" method=\"get\">\r\n    <button type=\"submit\">Create Patient</button>\r\n</form>\r\n</body>\r\n</html>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		List<Patient> patientList = (List<Patient>)params.get("patientList");
		String successMessage = (String)params.get("successMessage");
		render(jteOutput, jteHtmlInterceptor, patientList, successMessage);
	}
}
