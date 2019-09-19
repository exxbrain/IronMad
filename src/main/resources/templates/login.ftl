<#import "parts/common.ftl" as a>
<#import "parts/log_reg.ftl" as l>

<@a.page>
    <#if message??>
    <div class="alert ${(active)?string('alert-success', 'alert-warning')}" role="alert">
        ${message?ifExists}
    </div>
    </#if>
    <#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
        <div class="alert alert-danger" role="alert">
            ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
        </div>
    </#if>
<@l.login "/login" false />
</@a.page>