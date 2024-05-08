import React from "react";
import AuthService from "../../../Repository/authService";


interface DeviceRegistrationState {

}

interface DeviceRegistrationProps {
    currentUser: any
}

class DeviceRegistration extends React.PureComponent<DeviceRegistrationProps, DeviceRegistrationState> {

    constructor(props: DeviceRegistrationProps) {
        super(props);
        this.state = {}
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
            <div className="container">
                <form onSubmit={this.handleSubmit}>
                    <button type="submit" className="btn btn-primary">Register</button>
                </form>
            </div>
        )
    }
    handleSubmit = (event: any) => {
        event.preventDefault();
        AuthService.fido_register(this.props.currentUser.username, this.props.currentUser.name)

    }
}

export default DeviceRegistration;
