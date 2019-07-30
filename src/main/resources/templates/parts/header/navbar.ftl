<#import "../log_reg.ftl" as l>
<#include "../security.ftl">
<nav class="navbar navbar-expand-lg navbar-light bg-secondary">
    <a class="navbar-brand" href="/">IronMadness</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/">Главная</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/channel">Каналы</a>
            </li>
        </ul>
        <div class="dropdown">
            <button class="btn ${(user??)?string('btn-success', 'btn-secondary')} dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <div class="navbar-text">${name}</div>
            </button>
            <div class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownMenuButton">
                <#if !user??>
                <a class="dropdown-item" href="/login">Войти</a>
                <a class="dropdown-item" href="/registration">Регистрация</a>
                </#if>
                <#if user??>
                    <a class="dropdown-item" href="/user/profile">Профиль</a>
                    <#if isChannel>
                    <a class="dropdown-item" href="/user/channeledit">Совой канал</a>
                    </#if>
                </#if>
                <#if isAdmin>
                    <li class="nav-item">
                        <a class="dropdown-item" href="/user">Управление пользователями</a>
                    </li>
                </#if>
                <#if user??>
                    <@l.logaut/>
                </#if>
            </div>
        </div>
    </div>
</nav>