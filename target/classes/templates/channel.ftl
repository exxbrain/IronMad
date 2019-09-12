<#import "parts/common.ftl" as a>

<@a.page>
    <div class="card-columns">
    <#list channels as channel>
            <div class="card shadow mb-5 bg-white rounded">
                <img src="/img/${channel.avatar}" class="card-img-top">
                <div class="card-body">
                    <p class="card-text">${channel.text}</p>
                </div>
            </div>
    </#list>
    </div>
</@a.page>