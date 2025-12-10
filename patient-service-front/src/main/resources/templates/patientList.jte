@import com.medilabo.patient_service_front.models.Patient
@import java.util.List
@param List<Patient> patientList
@param String successMessage

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
@if(successMessage != null && !successMessage.isBlank())
    <p>${successMessage}</p>
@endif
@if(patientList.isEmpty())
    <p>There is no Patients in the system</p>
@else
    <table>
        <tr>
            <th>Firstname</th>
            <th>Family name</th>
            <th>Date of birth</th>
            <th>Gender</th>
            <th>address</th>
            <th>Phone number</th>
            <th>Action</th>
        </tr>
        @for(var patient : patientList)
            <tr>
                <td>${patient.getFirstName()}</td>
                <td>${patient.getLastName()}</td>
                <td>${patient.getFormattedBirthday()}</td>
                <td>${patient.getGender()}</td>
                <td>${patient.getAddress()}</td>
                <td>${patient.getPhoneNumber()}</td>
                <td>
                    <form action="/patient/update/${patient.getId()}" method="get" style="display: inline;">
                        <button type="submit">Edit</button>
                    </form>
                    <form action="/patient/delete/${patient.getId()}" method="get" style="display: inline;">
                        <button type="submit" class="link-button">Delete</button>
                    </form>
                </td>

            </tr>
        @endfor
    </table>
@endif

<form action="/patient/list" method="get">
    <button type="submit">Refresh List</button>
</form>
<form action="/patient/add" method="get">
    <button type="submit">Create Patient</button>
</form>
</body>
</html>