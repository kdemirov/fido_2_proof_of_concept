import React from 'react';
import Editor from '@draft-js-plugins/editor';
import {
    AtomicBlockUtils,
    convertFromRaw,
    convertToRaw,
    DraftHandleValue,
    EditorState,
    RichUtils
} from 'draft-js';
import {draftToMarkdown, markdownToDraft} from 'markdown-draft-js';


import {
    BlockquoteButton,
    BoldButton,
    CodeBlockButton,
    CodeButton,
    ItalicButton,
    OrderedListButton,
    UnorderedListButton,
    UnderlineButton
} from '@draft-js-plugins/buttons';


import createToolbarPlugin, {Separator} from '@draft-js-plugins/static-toolbar';
import createInlineToolbarPlugin
    from "@draft-js-plugins/inline-toolbar";
import createImagePlugin from '@draft-js-plugins/image';
import createMentionPlugin, {defaultSuggestionsFilter, MentionData} from '@draft-js-plugins/mention';

import editorStyles from '../../assets/styles/editor.module.css';
import '@draft-js-plugins/static-toolbar/lib/plugin.css'
import '@draft-js-plugins/inline-toolbar/lib/plugin.css'
import MarkdownPreview from "../MarkdownPreview";
import {ACCEPTED_TYPES_IMAGES} from "../../Constants/accepted_types_images";
import ImageAdd from "../ImageAdd/ImageAdd";
import LocalStorageRepository from "../../Repository/localStorageRepository";


export class AutocompleteItem {
    id: string;
    label: string;
    value: string;
    url: string;
    imageUrl?: string;

    constructor(id: string, label: string, value: string, url: string, imageUrl?: string) {
        this.id = id;
        this.label = label;
        this.value = value;
        this.imageUrl = imageUrl;
        this.url = url;
    }
}


interface EditAreaProps {
    value: string | undefined
    localStorageKey: string
    idEditingComponent?: string
    onChange: (value: string) => void
    mentionCandidates?: Array<AutocompleteItem>
    referenceCandidates?: Array<AutocompleteItem>
    renderAsInput: boolean
    showImageAdd: boolean
    showToolbar: boolean
    withPreview: boolean

}

interface EditAreaState {
    value: EditorState
    mentionSuggestions: MentionData[]
    referenceSuggestions: MentionData[]
    showPreview: boolean
    showInline: boolean
    key: string
    sourceValue: string

}


class EditArea extends React.Component<EditAreaProps, EditAreaState> {
    editAreaInput: HTMLTextAreaElement | HTMLInputElement | null = null
    private plugins: any;
    private mentionPlugin: any;
    private referencePlugin: any;
    private toolbarPlugin: any;
    private inlineToolbarPlugin: any;
    private imagePlugin: any;
    private editor: any;
    private localStorageRepo: LocalStorageRepository
    public static defaultProps = {
        renderAsInput: false,
        showImageAdd: true,
        withPreview: false,
        showToolbar: true
    }

    constructor(props: EditAreaProps) {
        super(props);
        this.state = {
            value: props.value ? this.markdownStringToEditorState(props.value) : EditorState.createEmpty(),
            mentionSuggestions: [],
            referenceSuggestions: [],
            showPreview: false,
            showInline: false,
            key: props.idEditingComponent !== undefined ? props.localStorageKey + props.idEditingComponent : props.localStorageKey,
            sourceValue: props.value ? props.value : "",


        }

        this.localStorageRepo = new LocalStorageRepository()
        this.mentionPlugin = createMentionPlugin({mentionTrigger: "@"})
        this.referencePlugin = createMentionPlugin({mentionTrigger: "#"})
        this.toolbarPlugin = createToolbarPlugin();
        this.inlineToolbarPlugin = createInlineToolbarPlugin();
        this.imagePlugin = createImagePlugin()
        this.plugins = [this.mentionPlugin, this.referencePlugin, this.toolbarPlugin, this.imagePlugin, this.inlineToolbarPlugin]

    }

    componentDidMount() {
        const content = this.localStorageRepo.findById(this.state.key)
        if (content) {
            this.setState({
                value: EditorState.createWithContent(convertFromRaw(JSON.parse(content))),
                sourceValue: this.editorStateToMarkdownString(EditorState.createWithContent(convertFromRaw(JSON.parse(content))))
            })

        } else {
            this.setState({value: EditorState.createEmpty()})
        }

    }

    componentDidUpdate(prevProps: Readonly<EditAreaProps>, prevState: Readonly<EditAreaState>, snapshot?: any) {
        const editorState = this.state.value.getCurrentContent();
        this.localStorageRepo.save(this.state.key, JSON.stringify(convertToRaw(editorState)))
        if (this.shouldAreaBeCleaned()) {
            this.setState({
                value: EditorState.createEmpty(),
                sourceValue: "",
                showPreview: false
            })
        } else if (this.shouldAreBeUpdated(prevState)) {
            this.setState({
                value: this.markdownStringToEditorState(this.props.value!!)
            })
        }
    }

    render() {
        return (
            <>
                {this.showToolbarButtons()}
                {this.showPreviewButtons()}
                {this.showEditorOrPreview()}
            </>
        )
    }


    private showImageAdd = () => {
        return (
            <ImageAdd
                editorState={this.state.value}
                onChange={this.onChange}
                modifier={this.imagePlugin.addImage}
            />
        )
    }
    private shouldAreaBeCleaned = (): boolean => {
        return this.props.value === undefined && this.editorStateToMarkdownString(this.state.value).length > 0
    }

    private shouldAreBeUpdated = (prevState: EditAreaState): boolean => {
        const cleanPropsValue = this.props.value?.replace("\r", "").replace("\n", "")
        return cleanPropsValue !== undefined && cleanPropsValue.length > 0 && this.editorStateToMarkdownString(this.state.value) === "" && this.editorStateToMarkdownString(prevState.value) === ""
    }


    private onSearchChange = ({value}: any) => {
        this.setState({
            mentionSuggestions: defaultSuggestionsFilter(value, this.mapAutocompleteItemToSuggestion(this.props.mentionCandidates)),
            referenceSuggestions: defaultSuggestionsFilter(value, this.mapAutocompleteItemToSuggestion(this.props.referenceCandidates))
        });
    };

    private mapAutocompleteItemToSuggestion = (items: Array<AutocompleteItem> | undefined) => {
        return items ?
            items.map(it => {
                return {
                    name: it.label,
                    link: it.url,
                    avatar: it.imageUrl || ""
                }
            })
            :
            []
    }

    private onChange = (value: EditorState) => {

        this.setState({
            value: value,
            sourceValue: this.editorStateToMarkdownString(this.state.value)
        }, () => {
            this.props.onChange(this.editorStateToMarkdownString(value))
        })

    }

    private focus = () => {
    };

    private onAddMention = () => {
    }

    private editorStateToMarkdownString = (state: EditorState): string => {
        return this.removeEscapeCharacters(draftToMarkdown(convertToRaw(state.getCurrentContent()), {
            entityItems: {
                mention: {
                    open() {
                        return `[@`;
                    },
                    close(entity: any) {
                        return `](${entity.data.mention.link})`;
                    }
                },
                ['#mention']: {
                    open() {
                        return `[#`;
                    },
                    close(entity: any) {
                        return `](${entity.data.mention.link})`;
                    }
                },

                'IMAGE': {
                    open: () => {
                        return '';
                    },
                    close: (entity: any) => `![](${entity.data.src})`
                },


            }
        }))
    }


    private markdownStringToEditorState = (markdownString: string): EditorState => {
        return EditorState.createWithContent(convertFromRaw(markdownToDraft(markdownString)));
    }
    private markdownSourceToEditorState = (markdownSource: string) => {
        const convertToDraft = (markdownSource: string) => {
            return markdownToDraft(markdownSource, {
                blockEntities: {
                    IMAGE: function (item: any) {
                        return {
                            type: 'atomic',
                            mutability: 'IMMUTABLE',
                            data: {
                                src: item.src,
                                alt: item.alt
                            }
                        }
                    }
                }
            })
        }
        return EditorState.createWithContent(convertFromRaw(convertToDraft(markdownSource)))
    }

    private showPlugins = () => {
        return (
            <>
                {this.state.showInline ? this.showInlineToolbar() : this.showToolbar()}
                {this.props.showImageAdd && this.showImageAdd()}
                {this.props.mentionCandidates && this.showMentionSuggestions()}
                {this.props.referenceCandidates && this.showReferenceSuggestions()}
            </>
        )
    }

    private showToolbar = () => {
        const {Toolbar} = this.toolbarPlugin
        return (
            <Toolbar>
                {
                    (externalProps: any) => (
                        <div>
                            <BoldButton {...externalProps} />
                            <ItalicButton {...externalProps} />
                            <UnderlineButton {...externalProps}/>
                            <CodeButton {...externalProps} />
                            <Separator {...externalProps} />
                            <UnorderedListButton {...externalProps} />
                            <OrderedListButton {...externalProps} />
                            <BlockquoteButton {...externalProps} />
                            <CodeBlockButton {...externalProps} />
                        </div>
                    )
                }
            </Toolbar>
        )
    }


    private showMentionSuggestions = () => {
        const MentionSuggestions = this.mentionPlugin.MentionSuggestions;
        return (
            <MentionSuggestions
                onSearchChange={this.onSearchChange}
                suggestions={this.state.mentionSuggestions}
                onAddMention={this.onAddMention}/>
        )
    }

    private showReferenceSuggestions = () => {
        const ReferenceSuggestions = this.referencePlugin.MentionSuggestions;
        return (
            <ReferenceSuggestions
                onSearchChange={this.onSearchChange}
                suggestions={this.state.referenceSuggestions}
                onAddMention={this.onAddMention}
            />
        )
    }

    private showPreviewButtons = () => {
        if (this.props.withPreview) {
            return (
                <div className="text-right">
                    {this.state.showPreview ?
                        <label onClick={() => this.setState({showPreview: false})}> {'SHOW_EDITOR'} </label>
                        : <label onClick={() => this.setState({showPreview: true})}>{'SHOW_PREVIEW'}</label>
                    }
                </div>
            )
        } else {
            return null
        }
    }
    private showToolbarButtons = () => {
        if (this.props.showToolbar) {
            return (
                <div className="text-center">
                    {
                        this.state.showInline ?
                            <label onClick={() => this.setState({showInline: false})}>{'INLINE'}</label> :
                            <label onClick={() => this.setState({showInline: true})}>{'STATIC'}</label>
                    }
                </div>
            )
        } else {
            return null
        }
    }


    private handleKeyCommand = (command: any, value: EditorState) => {
        const newValue = RichUtils.handleKeyCommand(value, command);

        if (newValue) {
            this.onChange(newValue)
            return 'handled' as DraftHandleValue
        }
        return 'not-handled' as DraftHandleValue;
    }


    private handlePastedFiles = (files: Blob[]) => {
        const {value} = this.state


        if (files && files.length) {
            const file = files[0];
            if (!ACCEPTED_TYPES_IMAGES.includes(file.type)) {
                return 'not-handled' as DraftHandleValue;
            }

            this.readImageAsDataUrl(file, (base64: string) => {
                const atomic = this.insertImageIntoEditor(value, base64);
                this.onChange(atomic)
                return 'not-handled' as DraftHandleValue;
            })
        }
        return 'handled' as DraftHandleValue;
    }

    private readImageAsDataUrl = (image: any, callback: any) => {
        const reader = new FileReader();
        reader.onloadend = () => {
            callback(reader.result)
        }
        reader.readAsDataURL(image);
    }

    private showInlineToolbar = () => {
        const {InlineToolbar} = this.inlineToolbarPlugin
        return (
            <InlineToolbar>
                {
                    (externalProps: any) => (
                        <div>
                            <BoldButton {...externalProps} />
                            <ItalicButton {...externalProps} />
                            <UnderlineButton {...externalProps}/>
                            <CodeButton {...externalProps} />
                            <UnorderedListButton {...externalProps} />
                            <OrderedListButton {...externalProps} />
                            <BlockquoteButton {...externalProps} />
                            <CodeBlockButton {...externalProps} />
                        </div>
                    )
                }
            </InlineToolbar>
        )
    }
    private insertImageIntoEditor = (value: any, base64: string) => {
        const contentState = this.state.value.getCurrentContent();
        const contentStateEntity = contentState.createEntity("IMAGE", 'IMMUTABLE', {src: base64})
        const entityKey = contentStateEntity.getLastCreatedEntityKey();
        return AtomicBlockUtils.insertAtomicBlock(
            value,
            entityKey,
            ' '
        );

    }

    private sourceChange = (event: any) => {
        this.setState({sourceValue: event.target.value, value: this.markdownSourceToEditorState(event.target.value)})
    }
    private showEditorOrPreview = () => {
        if (this.props.withPreview && this.state.showPreview) {
            return (

                <div className="row">
                    <div className="col-md-6">

                        <textarea className="form-control" value={this.state.sourceValue} style={{height: "100%"}}
                                  onChange={this.sourceChange}
                                  readOnly={false}/>

                    </div>
                    <div className="col-md-6">
                        <MarkdownPreview value={this.editorStateToMarkdownString(this.state.value)}/>
                    </div>
                </div>
            )
        } else {

            return (

                <div
                    className={`${editorStyles.editor} ${this.props.renderAsInput && editorStyles.editorAsInput}`}
                    onClick={this.focus}>
                    <Editor
                        editorState={this.state.value}
                        onChange={this.onChange}
                        plugins={this.plugins}
                        handleKeyCommand={this.handleKeyCommand}
                        handlePastedFiles={this.handlePastedFiles}
                        ref={(element) => {
                            this.editor = element;
                        }}
                        spellCheck={true}
                    />
                    {this.showPlugins()}
                </div>
            )
        }
    }

    private removeEscapeCharacters = (value: string | undefined): string => {
        if (!value) {
            return ''
        } else {
            return value.replace(/\\\*/g, "*")
                .replace(/\\`/g, "`")
                .replace(/\\~/g, "~")
        }
    }
}

export default EditArea;
