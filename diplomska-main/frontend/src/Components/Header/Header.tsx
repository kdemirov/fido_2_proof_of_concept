import React from "react";
import {Link} from "react-router-dom";
import LocalStorageRepository from "../../Repository/localStorageRepository";

interface HeaderProps {
    currentUser: any | undefined,
    currentUserLoggedOut: () => void

}

interface HeaderState {

}

class Header extends React.PureComponent<HeaderProps, HeaderState> {

    private localStorageRepository: LocalStorageRepository

    constructor(props: HeaderProps) {
        super(props);
        this.state = {}
        this.localStorageRepository = new LocalStorageRepository()
    }

    render() {

        let shouldShow = this.props.currentUser !== undefined
        return (

            <header>
                <nav className="navbar navbar-expand-sm navbar-dark bg-dark ">
                    <a className="navbar-brand" href="/discussions">Discussion</a>
                    <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                            aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"></span>
                    </button>
                    {shouldShow && this.getLoggedUser()}
                    {!shouldShow && this.getStartHeader()}
                </nav>
            </header>
        )
    }

    getLoggedUser = () => {

        return (
            <div className="collapse navbar-collapse" id="navbarNav">
                <ul className="nav navbar-nav mr-auto">
                    <li className="nav-item">
                        <a className="nav-link active" href="/discussions">Discussions</a>
                    </li>
                </ul>
                <p style={{color: "#FFF"}}>{this.props.currentUser.username}</p>
                <form className="form-inline mt-2 mt-md-0 ml-3">
                    <Link className="btn btn-outline-info my-2 my-sm-0" to={"/logout"}
                          onClick={this.handleLogOut}>Log Out</Link>
                </form>
                <form className="form-inline mt-2 mt-md-0 ml-3">
                    <Link className="btn btn-outline-info my-2 my-sm-0" to={"/register/fido"}>Register
                        Fido</Link>
                </form>
            </div>
        )
    }
    getStartHeader = () => {
        return (<div className="collapse navbar-collapse justify-content-end" id="navbarNav">
                <ul className="nav navbar-nav navbar-right">
                    <li className="nav-item ">
                        <form className="form-inline mt-2 mt-md-0 ml-3">
                            <Link className="btn btn-outline-info my-2 my-sm-0" to={"/login"}>Login</Link>
                        </form>
                    </li>
                    <li className="nav-item">
                        <form className="form-inline mt-2 mt-md-0 ml-3">
                            <Link className="btn btn-outline-info my-2 my-sm-0" to={"/login/fido"}>Login
                                Passwordless</Link>
                        </form>
                    </li>
                    <li className="nav-item ">
                        <form className="form-inline mt-2 mt-md-0 ml-3">
                            <Link className="btn btn-outline-info my-2 my-sm-0" to={"/register"}>Register</Link>
                        </form>
                    </li>

                </ul>
            </div>
        )
    }


    handleLogOut = () => {
        this.localStorageRepository.removeUser()
        this.props.currentUserLoggedOut()
        window.location.href = "/login"
    }


}

export default Header;
