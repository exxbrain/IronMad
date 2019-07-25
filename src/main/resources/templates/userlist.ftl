<#import "parts/common.ftl" as a>

<@a.page>
    Управление пользователями

    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Имя</th>
            <th scope="col">Роли</th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody class="table-hover">
        <#list users as us>
            <tr>
                <td>${us.username}</td>
                <td><#list us.roles as role>${role}<#sep>, </#list></td>
                <td><a href="/user/${us.id}"/>редактировать</td>
            </tr>
        </#list>
        </tbody>
    </table>
</@a.page>