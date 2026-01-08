@import com.medilabo.patient_service_front.models.DoctorNote
@import org.springframework.validation.BindingResult
@import java.util.List
@param String successMessage
@param String errorMessage

@param DoctorNote doctorNote
@param BindingResult result

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<h1>Page de creation</h1>
<a href="/patient/list">
    <h1>retour a la liste</h1>
</a>
@if(errorMessage != null && !errorMessage.isBlank())
    <p>${errorMessage}</p>
@endif
@if(successMessage != null && !successMessage.isBlank())
    <p>${successMessage}</p>
@endif
@if(doctorNote != null)
    <form action="/patient/${doctorNote.getPatientId()}/addNote" method="post">
        <input type="hidden" name="patientId" value="${doctorNote.getPatientId()}">
        
        <label for="note">Note</label>
        <input id="note"
               type="text"
               name="note"
        >
        @if(result != null && result.hasFieldErrors("note"))
            <p>${result.getFieldError("note").getDefaultMessage()}</p>
        @endif

        <button type="submit">Save</button>
    </form>
@endif
</body>
</html>