import React from "react";
import AuthService from "../../../Repository/authService";
import NotificationService from "../../../Repository/NotificationService";
import LocalStorageRepository from "../../../Repository/localStorageRepository";

interface LoginProps {

}

interface LoginState {
    username: string,
    password: string

}

class Login extends React.PureComponent<LoginProps, LoginState> {
    private localStorageRepository: LocalStorageRepository;

    constructor(props: any) {
        super(props);
        this.state = {
            username: "",
            password: ""
        }
        this.localStorageRepository = new LocalStorageRepository();
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

    templateProps = () => {
        return (
            <form onSubmit={this.handleSubmit}>
                <div className="form-group">
                    <input type="text"
                           name="username"
                           required
                           id="username"
                           onChange={this.handleChange}
                           placeholder="Username"
                           className="form-control"/>
                </div>
                <div className="form-group">
                    <input type="password"
                           name="password"
                           required
                           id="password"
                           onChange={this.handleChange}
                           placeholder="Password"
                           className="form-control"/>
                </div>
                <button type="submit" className="btn btn-primary">Login</button>
            </form>

        )
    }

    componentDidMount() {
        if (this.localStorageRepository.getUser()) {
            window.location.href = "/discussions";
        }
    }


    handleChange = (event: any) => {
        this.setState({
            [event.target.name]: event.target.value
        } as Pick<LoginState, keyof LoginState>)
    }

    handleSubmit = (event: any) => {
        event.preventDefault()
        AuthService.login(this.state.username, this.state.password)
            .then(() => {
                window.location.href = "/discussions";
            }).catch((err: any) => {
            NotificationService.error(err.message)
        })
    }

}

export default Login;
