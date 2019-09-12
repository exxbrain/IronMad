<#assign known = Session.SPRING_SECURITY_CONTEXT??>

<#if known>
    <#assign
        user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
        name = user.getUsername()
        isAdmin = user.isAdmin()
        isChannel = user.isChannalUser()
        >
    <#else>

    <#assign
        name = "Гость"
        isAdmin = false
        isChannel = false
    >
</#if>