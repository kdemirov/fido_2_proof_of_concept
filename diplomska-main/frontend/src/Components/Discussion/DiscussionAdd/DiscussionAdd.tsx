import React from "react";

interface DiscussionAddProps {
    onAddDiscussion: (name: string, description?: string) => void

}

interface DiscussionAddState {
    name: string,
    description: string,

}

class DiscussionAdd extends React.PureComponent<DiscussionAddProps, DiscussionAddState> {

    constructor(props: DiscussionAddProps) {
        super(props);
        this.state = {
            name: "",
            description: ""
        }
    }

    render() {
        return (
            <div className="container">
                <div className="row">
                    <div className="col-6">
                        <form onSubmit={this.handleSubmit}>
                            <div className="form-group">
                                <label htmlFor="name">Name:</label>
                                <input type="text" id="name"
                                       name="name"
                                       className="form-control"
                                       onChange={this.handleChangeName}/>
                            </div>
                            <div className="form-group">
                                <label htmlFor="description">Description:</label>
                                <input type="text" id="description"
                                       name="description"
                                       className="form-control"
                                       onChange={this.handleChangeDes}/>
                            </div>
                            <button className="btn btn-primary" type={"submit"}>Create new Discussion</button>
                        </form>

                    </div>
                </div>
            </div>
        )
    }

    handleChangeName = (event: any) => {
        this.setState({name: event.target.value})
    }
    handleChangeDes = (event: any) => {
        this.setState({description: event.target.value})
    }
    handleSubmit = (event: any) => {
        event.preventDefault()
        const name = this.state.name
        const description = this.state.description
        this.props.onAddDiscussion(name, description)
        window.location.href = "/discussions";
    }


}

export default DiscussionAdd;
