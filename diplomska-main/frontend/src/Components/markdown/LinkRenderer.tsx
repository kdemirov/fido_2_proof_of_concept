import React, {PropsWithChildren, PureComponent} from 'react';

interface LinkRendererProps {
    href: string
}

class LinkRenderer extends PureComponent<PropsWithChildren<LinkRendererProps>> {


    render() {
        if (this.props.href.match(/\.(jpe?g|png|gif)$/)) {
            return <img src={this.props.href} alt=""/>
        }

        return <a href={this.props.href} target="_blank" rel="noreferrer">{this.props.children}</a>
    }
}


export default LinkRenderer;