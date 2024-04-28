import React from 'react';
import gfm from "remark-gfm";
import CodeRenderer from "./markdown/CodeRenderer";
import LinkRenderer from "./markdown/LinkRenderer";
import ImageRenderer from "./markdown/ImageRenderer";
import ReactMarkdown, {NodeType} from "react-markdown";


interface MarkdownPreviewProps {
    value: string | undefined
    inline?: boolean
}

interface MarkdownPreviewState {

}

class MarkdownPreview extends React.PureComponent<MarkdownPreviewProps, MarkdownPreviewState> {
    render() {
        return <ReactMarkdown skipHtml={true}
                              plugins={[gfm]}
                              renderers={{code: CodeRenderer, link: LinkRenderer, image: ImageRenderer}}
                              source={this.props.value || ""}
                              allowedTypes={this.getAllowedTypes()}
                              unwrapDisallowed={this.unwrapDisallowed()}/>;
    }

    getAllowedTypes = (): Array<NodeType> | undefined => {
        return this.props.inline ? ['text', 'strong', 'delete', 'emphasis', 'link'] : undefined
    }

    unwrapDisallowed = (): boolean | undefined => {
        return this.props.inline ? true : undefined
    }
}

export default MarkdownPreview;