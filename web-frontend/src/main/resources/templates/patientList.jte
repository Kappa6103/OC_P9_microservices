@import com.medilabo.patient_service_front.models.DoctorNote
@import com.medilabo.patient_service_front.models.Patient
@import java.util.List
@param List<Patient> patientList
@param String successMessage
@param String errorMessage

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link rel="stylesheet" href="/css/style.css">
    <script src="https://cdn.jsdelivr.net/npm/htmx.org@2.0.8/dist/htmx.min.js" integrity="sha384-/TgkGk7p307TH7EXJDuUlgG3Ce1UVolAOFopFekQkkXihi5u/6OCvVKyz1W+idaz" crossorigin="anonymous"></script>
</head>
<body>
@if(successMessage != null && !successMessage.isBlank())
    <p>${successMessage}</p>
@elseif(errorMessage != null && !errorMessage.isBlank())
    <p>${errorMessage}</p>
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
            <th>Note</th>
            <th>Risk</th>
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
                    @for(DoctorNote note : patient.getNoteList())
                        <p>${note.getNote()}</p>
                    @endfor
                </td>
                <td>
                    <button
                            hx-get="/patient/${patient.getId()}/risk"
                            hx-swap="outerHTML"
                            hx-target="this"
                    >
                        Assess Patient
                    </button>
                </td>
                <td>
                    <form action="/patient/${patient.getId()}/update" method="get" style="display: inline;">
                        <button type="submit">Edit</button>
                    </form>
                    <form action="/patient/${patient.getId()}/delete" method="get" style="display: inline;">
                        <button type="submit" class="link-button">Delete</button>
                    </form>
                    <form action="/patient/${patient.getId()}/addNote" method="get" style="display: inline;">
                        <button type="submit" class="link-button">Add note</button>
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