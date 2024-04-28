import React from "react";
import AuthService from "../../../Repository/authService";
import LocalStorageRepository from "../../../Repository/localStorageRepository";

interface LoginState {

}

class FidoLogin extends React.PureComponent<any, LoginState> {
    private localStorageRepository: LocalStorageRepository

    constructor(props: any) {
        super(props);

        this.localStorageRepository = new LocalStorageRepository()
    }

    render() {

        return (
            <div className="col-md-12">
                <div className="card card-container">
                    {this.templateProps()}
                </div>
            </div>
        )
    }

    componentDidMount() {
        if (this.localStorageRepository.getUser()) {
            this.redirect();
        }
    }

    redirect = () => {
        window.location.href = "/discussions";
    }
    templateProps = () => {
        return (
            <form onSubmit={this.handleSubmit}>
                <div className="form-group">
                    <button className="btn btn-primary" type="submit">Login</button>
                </div>
            </form>
        )
    }
    handleSubmit = (event: any) => {
        event.preventDefault();
        AuthService.login_fido()
    }

}

export default FidoLogin;
