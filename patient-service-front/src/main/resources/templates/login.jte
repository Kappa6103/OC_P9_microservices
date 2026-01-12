@import com.medilabo.patient_service_front.models.LoginRequest
@import org.springframework.security.web.csrf.CsrfToken

@param LoginRequest loginRequest
@param CsrfToken _csrf
@param boolean logout
@param boolean error

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Login</title>
</head>
<body>
<h1>Please Log In</h1>
@if(error)
    <div>Invalid username and password.</div>
@endif
@if(logout)
    <div>You have been logged out.</div>
@endif

<form action="/login" method="post">
    @if(_csrf != null)
        <input type="hidden" name="${_csrf.getParameterName()}" value="${_csrf.getToken()}">
    @endif
    <div>
        <input type="text" name="username" placeholder="Username"/>
    </div>
    <div>
        <input type="password" name="password" placeholder="Password"/>
    </div>
    <input type="submit" value="Log in" />
</form>

</body>
</html>