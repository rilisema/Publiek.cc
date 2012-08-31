<head>
  <meta name="layout" content="main" />
  <title>Login</title>
</head>

<body>
<div class="body">
  <h1><g:message code="auth.login" default="Login" /></h1>

  <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
  </g:if>

  <g:hasErrors bean="${loginCommand}">
    <div class="errors">
      <g:renderErrors bean="${loginCommand}" as="list" />
    </div>
  </g:hasErrors>
  <form action='${postUrl}' method='POST' id='loginForm' class='cssform'>
    <div class="dialog">
      <table>
        <tbody>
          <tr class="prop">
            <td valign="top" class="name"><label for="authority"><g:message code="auth.username" default="User name" /></label></td>
            <td valign="top" class="value">
              <input type='text' class='text_' name='j_username' id='j_username' value='${request.remoteUser}' />
            </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name"><label for="authority"><g:message code="auth.password" default="Password" /></label></td>
            <td valign="top" class="value">
              <input type='password' class='text_' name='j_password' id='j_password' />
            </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name"><label for="authority"><g:message code="auth.remember" default="Remember me" /></label></td>
            <td valign="top" class="value">
        <input type='checkbox' class='chk' name='_spring_security_remember_me' id='remember_me' <g:if test='${hasCookie}'>checked='checked'</g:if> />
        </td>
        </tr>

        </tbody>
      </table>
    </div>
    <div class="buttons">
      <span class="button"><g:submitButton class="accept" name="login" value="${message(code: 'auth.login', 'default': 'Login')}" /></span>
    </div>
  </form>
</div>

<script type='text/javascript'>
<!--
(function(){
        document.forms['loginForm'].elements['j_username'].focus();
})();
// -->
</script>

</body>
