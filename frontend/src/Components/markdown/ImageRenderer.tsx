import React, {PureComponent} from "react";

interface ImagePreviewProps {
    src: string
    alt: string
    node: any
}

class ImageRenderer extends PureComponent<ImagePreviewProps> {


    render() {
        return (
            <img src={this.props.src} alt={this.props.alt}/>
        )
    }
}

export default ImageRenderer;