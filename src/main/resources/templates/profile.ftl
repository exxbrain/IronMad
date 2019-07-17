<#import "parts/common.ftl" as a>

<@a.page>
    <h3>${username}</h3>
    <form method="post">
        <div class="form-group row">
            <label for="exampleInputPassword1">Пароль</label>
                <input type="password" name="password" id="exampleInputPassword1"
                       class="form-control ${(passwordError??)?string('is-invalid', '')}"/>
                <#if passwordError??>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                </#if>
        </div>
        <div class="form-group row">
            <label for="exampleInputPassword2">Повторите пароль</label>
            <input type="password" name="password2"
                   id="exampleInputPassword2"
                       class="form-control ${(passwordError??)?string('is-invalid', '')}"/>
        </div>
        <div class="form-group row">
            <label for="exampleFormControlEmail">Email</label>
            <input type="email" name="email" class="form-control" id="exampleFormControlEmail" placeholder="som@som.com" value="${email!''}"/>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-primary" type="submit">Сохранить</button>
    </form>
</@a.page>