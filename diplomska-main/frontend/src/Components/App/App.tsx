import React from 'react';
import './App.css';
import Discussion from "../Discussion/Discussion"
import {BrowserRouter as Router, Route, Switch, Redirect} from "react-router-dom";
import {toast} from "react-toastify";
import FidoLogin from "../IdentifyAccessContext/Login/FidoLogin";
import Login from "../IdentifyAccessContext/Login/Login";
import RegisterComp from "../IdentifyAccessContext/Register/Register";
import DeviceRegistration from "../IdentifyAccessContext/Register/DeviceRegistration";
import Header from "../Header/Header";
import AuthService from "../../Repository/authService";
import LocalStorageRepository from "../../Repository/localStorageRepository";

toast.configure()

interface AppState {
    currentUser: any | undefined

}

class App extends React.Component<any, AppState> {
    private localStorageRepo: LocalStorageRepository;

    constructor(props: any) {
        super(props);
        this.state = {
            currentUser: undefined

        }
        this.localStorageRepo = new LocalStorageRepository()
    }

    render() {
        let redirect = this.state.currentUser === undefined ? <Redirect to={"/login"}/> : null;
        return (
            <main>
                <Router>

                    <Header currentUser={this.state.currentUser}
                            currentUserLoggedOut={this.currentUserLoggedOut}
                    />
                    <Switch>
                        <Route path={"/login/fido"}>
                            <FidoLogin/>
                        </Route>
                        <Route path={"/register/fido"}>
                            <DeviceRegistration currentUser={this.state.currentUser}/>
                        </Route>
                        <Route path={"/login"}>
                            <Login/>
                        </Route>
                        <Route path={"/register"}>
                            <RegisterComp/>
                        </Route>
                        <Route exact path={"/discussions"}>
                            <Discussion
                                getCurrentUser={this.getCurrentUser}
                                currentUser={this.state.currentUser}
                            />
                        </Route>
                        {redirect}
                    </Switch>
                </Router>
            </main>

        )
    }

    componentDidMount() {
        this.getCurrentUser();
    }

    getCurrentUser = () => {
        if (this.localStorageRepo.getUser()) {
            AuthService.getCurrentUser()
                .then((data: any) => {
                    this.setState({
                        currentUser: data.data
                    })
                })
        }
    }
    currentUserLoggedOut = () => {
        this.setState({
            currentUser: undefined
        })
    }
}

export default App;
