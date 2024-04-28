import axios from '../custom-axios/axios';
import {PersonId, DiscussionId} from "../model/model";
import NotificationService from "./NotificationService";

const CommentsService = {
    fetchDiscussion: (page: number, size: number) => {
        return axios.get("/discussion/pagination", {
            params: {
                page: page,
                size: size
            }});
    },
    getDiscussion: (id: string) => {
        return axios.get(`/discussion/${id}`).catch((err: any) => {
            NotificationService.error(err.message)
        })
    },
    getComments: (id: string) => {
        return axios.get(`/discussion/comments/${id}`
      ).catch((err) => {
            console.log(err.message)
        })
    },
    createDiscussion: (name: string, description: string, creator: PersonId) => {
        return axios.post("/discussion/add", {
            "name": name,
            "description": description,
            "creator": creator
        }).catch((err: any) => {
          NotificationService.error(err.message)
        })
    },
    addComment: (discussionId: DiscussionId, author: PersonId, body: string) => {
        return axios.post("/discussion/comments/add", {
            "discussionId": discussionId,
            "author": author,
            "body": body,
            "mentions": [],
            "taskReferences": []
        }).catch((err: any) => {
            NotificationService.error(err.message)
        })
    },
    editComment: (discussionId: object, commentId: object, newBody: string, mentions: [], taskReferences: []) => {
        return axios.post("/discussion/comments/edit", {
            "discussionId": discussionId,
            "commentId": commentId,
            "newBody": newBody,
            "mentions": mentions,
            "taskReferences": taskReferences
        })
    },
    deleteComment: (discussionId: string, commentId: string) => {
        return axios.delete(`/discussion/comments/delete?discussionId=${discussionId}&commentId=${commentId}`)

    },
    getNumberOfDiscussions: () => {
        return axios.get("/discussion/number")
    },
    findByName: (name: string) => {
        return axios.get(`/discussion/name?name=${name}`)
    }


}
export default CommentsService;
