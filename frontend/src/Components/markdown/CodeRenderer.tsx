import React from 'react';
import {Prism as SyntaxHighlighter} from "react-syntax-highlighter";

interface CodeRendererProps {
    value: string
    language: string
}

function CodeRenderer(props: CodeRendererProps) {
    return (
        <SyntaxHighlighter language={props.language}>
            {props.value || ""}
        </SyntaxHighlighter>
    );
}

export default CodeRenderer;

/*
coy
dark
funky
okaidia
solarizedlight
tomorrow
twilight
prism
a11yDark
atomDark
base16AteliersulphurpoolLight
cb
coldarkCold
coldarkDark
coyWithoutShadows
darcula
dracula
duotoneDark
duotoneEarth
duotoneForest
duotoneLight
duotoneSea
duotoneSpace
ghcolors
hopscotch
materialDark
materialLight
materialOceanic
nord
pojoaque
shadesOfPurple
synthwave84
vs
vscDarkPlus
xonokai
* */