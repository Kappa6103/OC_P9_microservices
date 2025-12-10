@import com.medilabo.patient_service_front.models.*
@import org.springframework.validation.BindingResult
@param Patient patient
@param BindingResult result
<!-- dsdfsdf -->
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
<a href="/patient/list">
    <h1>retour a la liste</h1>
</a>
<form action="/patient/update" method="post">
    <input type="hidden" name="id" value="${patient.getId()}">

    <label for="fname">First name</label>
    <input id="fname"
           type="text"
           value="${patient.getFirstName()}"
           name="firstName"
    >
    @if(result != null && result.hasFieldErrors("firstName"))
        <p>${result.getFieldError("firstName").getDefaultMessage()}</p>
    @endif

    <label for="lname">Last name</label>
    <input id="lname"
           type="text"
           value="${patient.getLastName()}"
           name="lastName"
    >
    @if(result != null && result.hasFieldErrors("lastName"))
        <p>${result.getFieldError("lastName").getDefaultMessage()}</p>
    @endif
    <label for="dob">Date of birth</label>
    <input id="dob"
           type="date"
           value="${patient.getBirthday() == null ? "" : patient.getBirthday().toString()}"
           name="birthday"
    >
    @if(result != null && result.hasFieldErrors("birthday"))
        <p>${result.getFieldError("birthday").getDefaultMessage()}</p>
    @endif
    <label for="gender">Gender</label>
    <select id="gender"
            name="gender">
        <option value="${Gender.MALE}">A Dude</option>
        <option value="${Gender.FEMALE}">A Woman</option>
        <option value="${Gender.OTHER}">Other</option>
    </select>
    @if(result != null && result.hasFieldErrors("gender"))
        <p>${result.getFieldError("gender").getDefaultMessage()}</p>
    @endif
    <label for="address">Address</label>
    <input id="address"
           type="text"
           name="address"
           value="${patient.getAddress()}"
    >
    <label for="phoneNumber">Phone Number</label>
    <input id="phoneNumber"
           type="text"
           name="phoneNumber"
           value="${patient.getPhoneNumber()}"
    >
    <button type="submit">Save</button>
</form>

</body>
</html>