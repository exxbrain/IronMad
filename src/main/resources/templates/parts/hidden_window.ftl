    <div class="overlay" title="окно"></div>
    <div class="popup log-reg-style">
        <div class="close_window">x</div>
        <p>Придумайте имя</p>
        <form action="/api" method="post">
                <div class="form-group row">
                    <label class="col-sm-2 col-form-label">Логин: </label>
                    <div class="col-sm-10">
                        <input type="text" name="username" id="name_api"
                               class="input-style form-control"/>
                            <div id="error_name_api" class="invalid-feedback error_window_api" style="opacity: 0; visibility: hidden">
                                Такой логин уже есть, придумай другой
                            </div>
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                        <button id="btn_api_window" class="btn btn-primary" style="margin-top: 4px" type="submit">Создать</button>
                    </div>
                </div>
        </form>
    </div>
    </div>
