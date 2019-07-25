<#import "parts/common.ftl" as a>

<@a.page>
    <form enctype="multipart/form-data" action="/user/channeledit" method="post" >
        <div class="form-group">
            <label for="exampleFormControlInput1">Имя канала</label>
            <input type="text" name="name" class="form-control ${(nameError??)?string('is-invalid', '')}"
                   id="exampleFormControlInput1" value="<#if channel??>${channel.name}</#if>" placeholder="name">
            <#if nameError??>
                <div class="invalid-feedback">
                    ${nameError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <label for="exampleFormControlTextarea1">Описание</label>
            <textarea class="form-control ${(textError??)?string('is-invalid', '')}" name="text"
                      id="exampleFormControlTextarea1" rows="3"><#if channel??>${channel.text}</#if></textarea>
            <#if textError??>
                <div class="invalid-feedback">
                    ${textError}
                </div>
            </#if>
        </div>
        <div class="custom-file">
            <input type="file" name="filePath" id="customFile">
            <label class="custom-file-label" for="customFile">Картинка</label>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-primary mt-2" type="submit">Создать</button>
    </form>
</@a.page>