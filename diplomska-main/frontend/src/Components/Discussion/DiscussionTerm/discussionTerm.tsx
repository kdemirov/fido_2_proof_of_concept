import React from "react";
import {Link} from "react-router-dom";

interface DiscussionTermProps {
    discussionId: {
        id: string
    }
    name: string,
    description: string,
    onSelect: (id: string) => void
}

class DiscussionTerm extends React.PureComponent<DiscussionTermProps, any> {


    render() {
        return (
            <div className="card" style={{width: "18rem"}}>
                <div className="card-body">
                    <h6 className="card-title">{this.props.name}</h6>
                    <p className="card-text">{this.props.description}</p>
                    <Link className="card-link"
                          onClick={() => this.props.onSelect(this.props.discussionId.id)}
                          to={`/discussions/preview/${this.props.discussionId.id}`}>Preview</Link>
                </div>
            </div>
        )
    }

}

export default DiscussionTerm;