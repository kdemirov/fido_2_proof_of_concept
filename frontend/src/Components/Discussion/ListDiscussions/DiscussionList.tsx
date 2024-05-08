import React from "react";
import DiscussionTerm from "../DiscussionTerm/discussionTerm";
import ReactPaginate from "react-paginate";
import {Link} from "react-router-dom";

interface DiscussionProps {
    discussions: [],
    onSelect: (id: string) => void,
    onPageSelect: (page: number, size: number) => void,
    numberOfDiscussions: number
    onSearch: (text: string) => void,
    hasError: boolean,
    error: string
}


interface DiscussionState {
    page: number,
    size: number,
    searchText: string

}

class DiscussionList extends React.PureComponent<DiscussionProps, DiscussionState> {
    constructor(props: any) {
        super(props);
        this.state = {
            page: 0,
            size: 30,
            searchText: ""
        }
    }

    render() {
        const pageCount = Math.ceil(this.props.numberOfDiscussions / this.state.size)
        const discussionTiles = this.props.discussions.map((item: any) => <DiscussionTerm key={item.id.id}
                                                                                          discussionId={item.id}
                                                                                          name={item.name}
                                                                                          description={item.description}
                                                                                          onSelect={this.props.onSelect}/>)
        const error = this.props.hasError ? <span>{this.props.error}</span> : null
        return (
            <div className="container">

                <div className="row">
                    <form onSubmit={this.handleSubmit}>
                        <div className="row">
                            <div className="col-md-6">
                                <div className="form-group">
                                    <input type="text" id="search" placeholder="Search by name"
                                           onChange={this.handleChange}/>
                                </div>
                            </div>
                            <div className="col-md-6">
                                <button className="btn btn-primary">Search</button>
                            </div>
                        </div>
                    </form>
                </div>
                <div className="row">
                    {discussionTiles}
                </div>
                <div className="row">
                    <div className="col-12">
                        <Link className="btn btn-primary" to={"/discussions/add"}>Create New Discussion</Link>
                    </div>
                </div>
                <ReactPaginate
                    previousLabel={"back"}
                    nextLabel={"next"}
                    breakLabel={<a href={"/#"}>..</a>}
                    pageClassName={"ml-1"}
                    pageCount={pageCount}
                    marginPagesDisplayed={2}
                    pageRangeDisplayed={7}
                    onPageChange={this.handlePageClick}
                    containerClassName={"pagination"}
                    activeClassName={"active"}/>
                <div className="alert-danger">
                    {error}
                </div>
            </div>

        )
    }


    handleSubmit = (event: any) => {
        event.preventDefault();
        this.props.onSearch(this.state.searchText)
    }

    handlePageClick = (data: any) => {
        const selected = data.selected
        this.setState({
            page: selected
        })
        this.props.onPageSelect(selected, this.state.size)
    }
    handleChange = (event: any) => {
        this.setState({
            searchText: event.target.value
        })
    }
}

export default DiscussionList;