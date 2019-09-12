<#import "parts/common.ftl" as a>

<@a.page>
    <h3 class="mb-3">Настройка пользователя</h3>
    <form action="/user" method="post">
        <div class="form-group">
            <label for="exampleInputEmail1">Имя</label>
            <input type="text" name="username" class="form-control mb-3" id="exampleInputEmail1" value="${user.username}">
            <#list roles as role>
                <div class="form-group form-check">
                    <label class="form-check-label" for="exampleCheck1">
                        <input type="checkbox" class="form-check-input" id="exampleCheck1"
                               name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}</label>
                </div>
            </#list>
            <input type="hidden" value="${user.id}" name="userId">
            <input type="hidden" value="${_csrf.token}" name="_csrf"/>
            <button type="submit" class="btn btn-primary">Сохранить</button>
        </div>
    </form>
</@a.page>