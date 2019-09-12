<#import "parts/common.ftl" as a>
<#import "parts/log_reg.ftl" as r>
<@a.page>
    <div class="md-1">Add new user</div>
    ${message?ifExists}
    <@r.login "/registration" true />
</@a.page>