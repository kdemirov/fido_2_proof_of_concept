import React from "react";
import MarkdownPreview from "../../MarkdownPreview";
import EditArea from "../../OriginalArea/EditArea";
import LocalStorageRepository from "../../../Repository/localStorageRepository";
import {DiscussionId, PersonId} from "../../../model/model";


interface DiscussionPreviewProps {
    currentDiscussion: any
    onAddComment: (id: DiscussionId, creator: PersonId, body: string) => void
    onDelete: (discussionId: string, commentId: string) => void
    onEdit: (discussionId: any, commentId: any, newBody: string) => void
    hasError: boolean
    error: string,
    currentUser: any
}

interface DiscussionPreviewState {
    value: string | undefined,
    comments: [],
    isEdited: boolean,
    currentCommentId: any,
    user: any

}

class DiscussionPreview extends React.PureComponent<DiscussionPreviewProps, DiscussionPreviewState> {

    private storageRepository: LocalStorageRepository;

    constructor(props: DiscussionPreviewProps) {
        super(props);
        this.state = {
            value: "",
            comments: [],
            isEdited: false,
            currentCommentId: {},
            user: {}
        }
        this.storageRepository = new LocalStorageRepository();
    }


    render() {
        let editArea = this.getEditArea();
        let error = this.props.hasError ? <span>{this.props.error}</span> : null;
        let commentTiles = this.props.currentDiscussion.comments?.map((comment: any) => this.getComments(comment))
        return (
            <div className="container">
                <div className="row">
                    <div className="page-header" style={{width:"100%"}}>
                    <h4 className="display-4">{this.props.currentDiscussion.discussionView?.name}</h4>
                        <small>{this.props.currentDiscussion.discussionView?.description}</small>
                    <hr className="my4"/>
                    </div>
                </div>
                {commentTiles}
                <div className="row">
                    {editArea}
                </div>
                <div className="alert-danger">
                    {error}
                </div>
            </div>
        )
    }
    getEditButton=(comment:any)=>{
        return(
            <button onClick={() => this.handleEdit(comment.body, comment.id)}
                    className="btn btn-success btn-sm border-0 px-3" type="button">
                Edit
            </button>
        )
    }
    getDeleteButton=(comment:any)=>{
        return (
            <span
                className="mr-3 delete"
                onClick={() => this.props.onDelete(this.props.currentDiscussion.discussionView.id.id,
                    comment.id.id)}>
                Delete
            </span>
        )
    }
    getComments = (comment: any) => {
        let visible=comment.authorName === this.props.currentUser.name
        const editButton=visible?this.getEditButton(comment):null;
        const deleteButton=visible?this.getDeleteButton(comment):null;
        return (
            <div key={comment.id.id} className="d-flex flex-row align-items-center add-comment p-2 bg-white rounded">
                <div className="p-3 bg-white mt-2 rounded">
                    <div className="d-flex justify-content-between">
                        <div className="d-flex flex-row user"><img className="rounded-circle img-fluid img-responsive"
                                                                   src="https://i.imgur.com/CFpa3nK.jpg"
                                                                   alt={"person_image"} width="40"/>
                            <div className="d-flex flex-column ml-2"><span
                                className="font-weight-bold">{comment.authorName}</span>
                                <span className="day">1 day ago</span></div>
                        </div>
                    </div>
                    <div className="comment-text text-justify mt-2">
                        <MarkdownPreview value={comment.body}/>
                    </div>
                     <div className="d-flex justify-content-end align-items-center comment-buttons mt-2 text-right">
                    {deleteButton}
                    {editButton}
                    </div>

                </div>
            </div>
        )
    }
    getEditArea = () => {
        if (this.state.isEdited) {
            return this.getEditorForEdit()
        } else {
            return this.getEditorForAdd();
        }
    }
    getEditorForEdit = () => {
        return (
            <div className="col-sm-6">
                <EditArea onChange={this.onChange} value={this.state.value}
                          localStorageKey={this.props.currentDiscussion.discussionView?.name}
                          idEditingComponent={this.state.currentCommentId}
                withPreview={true}/>
                <button className="btn btn-primary" onClick={this.onEdit}>Edit</button>
            </div>
        )
    }
    getEditorForAdd = () => {
        return (
            <div className="col-sm-6">
                <EditArea onChange={this.onChange} value={this.state.value}
                          localStorageKey={this.props.currentDiscussion.discussionView?.name}
                withPreview={true}
                />
                <button className="btn btn-primary" onClick={this.onSubmit}>Add</button>
            </div>
        )
    }
    handleEdit = (body: string, commentId: any) => {
        this.setState({
            value: body,
            isEdited: true,
            currentCommentId: commentId
        })
    }
    onEdit = () => {
        this.props.onEdit(this.props.currentDiscussion.discussionView.id,
            this.state.currentCommentId,
            this.state.value ? this.state.value : "")
        this.setState({
            isEdited: false,
            value: undefined
        })
        this.storageRepository.delete(this.props.currentDiscussion.discussionView.name + this.state.currentCommentId)

    }
    onChange = (value: any) => {
        this.setState({
            value: value
        })
    }
    onSubmit = () => {
        this.props.onAddComment(this.props.currentDiscussion.discussionView.id,
            new PersonId(this.props.currentUser.userID),
            this.state.value ? this.state.value : "");
        this.setState({
            value: undefined
        })
        this.storageRepository.delete(this.props.currentDiscussion.discussionView.name)
    }


}

export default DiscussionPreview;
