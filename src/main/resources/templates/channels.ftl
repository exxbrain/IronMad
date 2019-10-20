<#import "parts/common.ftl" as a>

<@a.page>
    <div class="card-columns">
    <#list channels as channel>
            <div style="background-image: url(/img/${channel.avatar}); border: 0px"
                 class="card shadow mb-5 rounded img_square">
            </div>
    </#list>
    </div>
</@a.page>