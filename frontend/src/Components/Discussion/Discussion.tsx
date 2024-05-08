import React from "react";
import {BrowserRouter as Router, Route} from "react-router-dom";
import DiscussionPreview from "./DiscussionPreview/DiscussionPreview";
import DiscussionAdd from "./DiscussionAdd/DiscussionAdd";
import CommentsService from "../../Repository/commentRepository";
import {DiscussionId, PersonId} from "../../model/model";
import DiscussionList from "./ListDiscussions/DiscussionList";
import LocalStorageRepository from "../../Repository/localStorageRepository";

interface DiscussionProps {
    getCurrentUser: () => void,
    currentUser: any
}

interface DiscussionState {
    discussions: [],
    numberOfDiscussions: number,
    hasError: boolean,
    error: string,
    currentDiscussion: any,
    redirect: boolean,

}

class Discussion extends React.Component<DiscussionProps, DiscussionState> {
    private localStorageRepo: LocalStorageRepository;

    constructor(props: any) {
        super(props);
        this.state = {
            discussions: [],
            numberOfDiscussions: 0,
            hasError: false,
            error: "",
            currentDiscussion: {},
            redirect: false
        }
        this.localStorageRepo = new LocalStorageRepository();
    }

    render() {
        return (
            <Router>
                <Route exact={true} path={"/discussions/preview/:id"}>
                    <DiscussionPreview
                        onAddComment={this.addComment}
                        currentDiscussion={this.state.currentDiscussion}
                        onEdit={this.editComment}
                        onDelete={this.deleteComment}
                        error={this.state.error}
                        hasError={this.state.hasError}
                        currentUser={this.props.currentUser}/>
                </Route>
                <Route exact={true} path={"/discussions/add"}>
                    <DiscussionAdd onAddDiscussion={this.createDiscussion}/>
                </Route>
                <Route path={"/discussions"} exact={true}>
                    <DiscussionList
                        hasError={this.state.hasError}
                        error={this.state.error}
                        discussions={this.state.discussions}
                        onSelect={this.getDiscussion}
                        onSearch={this.onSearch}
                        onPageSelect={this.loadDiscussionsPagination}
                        numberOfDiscussions={this.state.numberOfDiscussions}/>
                </Route>
            </Router>
        )
    }

    componentDidMount() {
        this.checkCurrentUser()
        this.loadDiscussions()
        this.getNumberOfDiscussions()
        this.props.getCurrentUser();
    }


    loadDiscussions = () => {
        CommentsService.fetchDiscussion(0, 30)
            .then((data: any) => {
                this.setState({
                    discussions: data.data
                })
            })
    }
    checkCurrentUser = () => {
        if (this.localStorageRepo.getUser() === null) {
            window.location.href = "/login"
        }
    }
    loadDiscussionsPagination = (page: number, size: number) => {
        CommentsService.fetchDiscussion(page, size)
            .then((data: any) => {
                this.setState({
                    discussions: data.data
                })
            })
    }

    onSearch = (text: string) => {
        CommentsService.findByName(text)
            .then((data) => {
                this.setState({
                    discussions: data.data
                })
            })
    }
    getNumberOfDiscussions = () => {
        CommentsService.getNumberOfDiscussions()
            .then((data: any) => {
                this.setState({
                    numberOfDiscussions: data.data
                })
            })
    }
    createDiscussion = (name: string, description?: string) => {
        console.log(this.props.currentUser, '!!!');
        CommentsService.createDiscussion(name, description ? description : "", new PersonId(this.props.currentUser.userID))
            .then(() => {
                this.loadDiscussions();
            })
    }
    editComment = (discussionId: any, commentId: any, newBody: string) => {

        CommentsService.editComment(discussionId, commentId, newBody, [], [])
            .then(() => {
                this.getDiscussion(discussionId.id)
            })
    }
    getDiscussion = (id: any) => {
        CommentsService.getDiscussion(id)
            .then((data: any) => {
                this.setState({
                    currentDiscussion: data.data
                })
            })

    }
    addComment = (id: DiscussionId, author: PersonId, body: string) => {
        CommentsService.addComment(id, author, body)
            .then(() => {
                this.getDiscussion(id.id)
            })
    }
    deleteComment = (discussionId: string, commentId: string) => {
        CommentsService.deleteComment(discussionId, commentId)
            .then(() => {
                this.getDiscussion(discussionId)
            })
    }
}

export default Discussion;
