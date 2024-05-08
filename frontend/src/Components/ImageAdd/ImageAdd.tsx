import React from "react";
import imageAddStyles from "../../assets/styles/ImageAdd.module.css";

interface ImageAddProps {
    editorState: any
    onChange: any
    modifier: any
}

interface ImageAddState {
    url: string,
    open: boolean,
    preventNextClose: boolean
}

class ImageAdd extends React.PureComponent<ImageAddProps, ImageAddState> {

    // Start the popover closed
    constructor(props: ImageAddProps) {
        super(props);
        this.state = {
            url: '',
            open: false,
            preventNextClose: false
        }
    }


    render() {
        const popoverClassName = this.state.open ?
            imageAddStyles.addImagePopover :
            imageAddStyles.addImageClosedPopover;
        const buttonClassName = this.state.open ?
            imageAddStyles.addImagePressedButton :
            imageAddStyles.addImageButton;

        return (
            <div className={imageAddStyles.addImage}>
                <button
                    className={buttonClassName}
                    onMouseUp={this.openPopover}
                    type="button"
                >
                    +
                </button>
                <div
                    className={popoverClassName}
                    onClick={this.onPopoverClick}
                >
                    <input
                        type="text"
                        placeholder="Paste the image url …"
                        className={imageAddStyles.addImageInput}
                        onChange={this.changeUrl}
                        value={this.state.url}
                    />
                    <button
                        className={imageAddStyles.addImageConfirmButton}
                        type="button"
                        onClick={this.addImage}
                    >
                        Add
                    </button>
                </div>
            </div>
        );
    }

    // When the popover is open and users click anywhere on the page,
    // the popover should close
    componentDidMount() {
        document.addEventListener('click', this.closePopover);
    }

    componentWillUnmount() {
        document.removeEventListener('click', this.closePopover);
    }

    // Note: make sure whenever a click happens within the popover it is not closed
    onPopoverClick = () => {
            this.setState({
                preventNextClose: true
            })

    }

    openPopover = () => {
        if (!this.state.open) {
            this.setState({
                preventNextClose: true,
                open: true
            });
        }
    };

    closePopover = () => {
        if (!this.state.preventNextClose && this.state.open) {
            this.setState({
                open: false,
            });
        }
        this.setState({
            preventNextClose:false
        })
    };

    addImage = () => {
        const {editorState, onChange} = this.props;
        onChange(this.props.modifier(editorState, this.state.url));
    };

    changeUrl = (evt: any) => {
        this.setState({url: evt.target.value});
    }

}

export default ImageAdd