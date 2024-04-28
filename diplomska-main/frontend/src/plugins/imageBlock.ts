import Draft,{EditorState} from "draft-js";


const ImageRegexp = /^!\[([^\]]*)]\s*\(([^)"]+)( "([^)"]+)")?\)/
const imageBlock = (remarkable:any) => {
    remarkable.block.ruler.before('paragraph', 'image', (state:any, startLine:any, endLine:any, silent:any) => {
        const pos = state.bMarks[startLine] + state.tShift[startLine]
        const max = state.eMarks[startLine]

        if (pos >= max) {
            return false
        }
        if (!state.src) {
            return false
        }
        if (state.src[pos] !== '!') {
            return false
        }

        var match = ImageRegexp.exec(state.src.slice(pos))
        if (!match) {
            return false
        }

        // in silent mode it shouldn't output any tokens or modify pending
        if (!silent) {
            state.tokens.push({
                type: 'image_open',
                src: match[2],
                alt: match[1],
                lines: [ startLine, state.line ],
                level: state.level
            })

            state.tokens.push({
                type: 'inline',
                content: match[4] || '',
                level: state.level + 1,
                lines: [ startLine, state.line ],
                children: []
            })

            state.tokens.push({
                type: 'image_close',
                level: state.level
            })
        }

        state.line = startLine + 1

        return true
    })
}
export default imageBlock;