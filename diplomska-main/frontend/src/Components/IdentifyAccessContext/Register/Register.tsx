import React from "react";
import AuthService from "../../../Repository/authService";
import NotificationService from "../../../Repository/NotificationService";
import LocalStorageRepository from "../../../Repository/localStorageRepository";


interface RegisterState {
    name: string,
    surname: string,
    username: string,
    password: string
}

class RegisterComp extends React.PureComponent<any, RegisterState> {
    private localStorageRepo: LocalStorageRepository

    constructor(props: any) {
        super(props);
        this.state = {
            name: "",
            surname: "",
            username: "",
            password: ""
        }
        this.localStorageRepo = new LocalStorageRepository();
    }


    render() {
        return (
            <div className="col-md-12">
                <div className="card card-container">
                    {this.getTemplateProps()}
                </div>
            </div>
        )
    }

    getTemplateProps = () => {
        return (
            <form onSubmit={this.handleSubmit}>
                <div className="form-group">
                    <input
                        type="text"
                        name="name"
                        required
                        onChange={this.handleChange}
                        id="name"
                        className="form-control"
                        placeholder="Name:"
                    />
                </div>
                <div className="form-group">
                    <input
                        type="text"
                        name="surname"
                        required
                        onChange={this.handleChange}
                        id="surname"
                        className="form-control"
                        placeholder="Surname:"
                    />
                </div>
                <div className="form-group">
                    <input
                        type="text"
                        name="username"
                        id="username"
                        required
                        onChange={this.handleChange}
                        className="form-control"
                        placeholder="Username:"
                    />
                </div>
                <div className="form-group">
                    <input
                        type="password"
                        name="password"
                        required
                        id="password"
                        onChange={this.handleChange}
                        className="form-control"
                        placeholder="Password:"
                    />
                </div>
                <button type="submit" className="btn btn-primary">Register</button>
            </form>
        )
    }
    handleChange = (event: any) => {
        this.setState({
            [event.target.name]: event.target.value
        } as Pick<RegisterState, keyof RegisterState>)
    }

    componentDidMount() {
        if (this.localStorageRepo.getUser()) {
            this.redirect("/discussions")
        }
    }

    redirect = (path: string) => {
        window.location.href = path
    }
    handleSubmit = (event: any) => {
        event.preventDefault()
        AuthService.register(this.state.name, this.state.surname, this.state.username, this.state.password)
            .then(() => {
                NotificationService.success("Registration was successfully")
                this.redirect("/login");
            }).catch((err: any) => {
            NotificationService.error(err.message)
        })

    }
}

export default RegisterComp;
