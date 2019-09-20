<#macro login path isRegisterForm vkcode>
    <div class="log-reg-style" style="">
    <form action="${path}" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Логин:</label>
            <div class="col-sm-10">
                <input type="text" name="username" value="<#if user??>${user.username}</#if>"
                       class="input-style form-control ${(usernameError??)?string('is-invalid', '')}"/>
                <#if usernameError??>
                    <div class="invalid-feedback">
                        ${usernameError}
                    </div>
                </#if>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Пароль:</label>
            <div class="col-sm-10">
                <input type="password" name="password"
                       class="input-style form-control ${(passwordError??)?string('is-invalid', '')}" placeholder="Password"/>
                <#if passwordError??>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                </#if>
            </div>
        </div>
        <#if isRegisterForm>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Пароль:</label>
                <div class="col-sm-10">
                    <input type="password" name="password2"
                           class="input-style form-control ${(password2Error??)?string('is-invalid', '')}"
                           placeholder="Return password"/>
                    <#if password2Error??>
                        <div class="invalid-feedback">
                            ${password2Error}
                        </div>
                    </#if>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Email:</label>
                <div class="col-sm-10">
                    <input type="email" name="email" value="<#if user??>${user.email}</#if>"
                           class="input-style form-control ${(emailError??)?string('is-invalid', '')}"
                           placeholder="som@som.com"/>
                    <#if emailError??>
                        <div class="invalid-feedback">
                            ${emailError}
                        </div>
                    </#if>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="g-recaptcha" data-sitekey="6LcN3KwUAAAAAMiWJa9HZjvGbB5uK7jnuz1mHcMY"></div>
                <#if captchaError??>
                    <div class="alert alert-danger" role="alert">
                        ${captchaError}
                    </div>
                </#if>
            </div>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <#if !isRegisterForm><a href="/registration">Новый узер</a></#if>
        <button class="btn btn-primary" type="submit"><#if isRegisterForm>Создать<#else>Войти</#if></button>
    </form>
        <#if !isRegisterForm><a class="vkapi" href="${vkcode}">VK</a></#if>
    </div>
</#macro>

<#macro logaut>
    <div class="ml-2">
        <form action="/logout" method="post">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input type="submit" value="Sing Out" class="btn btn-primary">
        </form>
    </div>
</#macro>