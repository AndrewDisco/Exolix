import {Lexer, Token, TokenStructure} from "@skylixgh/elixor-lexer";

interface TokenTypes extends TokenStructure {
    leftParenthesis: RegExp;
    rightParenthesis: RegExp;
    quote: RegExp;
    text: RegExp;
    newLine: RegExp;
}

const tokenTree: TokenTypes = {
    newLine: /^\n/,
    leftParenthesis: /^\(/,
    rightParenthesis: /^\)/,
    quote: /^"/,
    text: /^[^"^()]+/,
}

const processor = new Lexer<TokenTypes>(tokenTree, `print("Hello World")\n`.repeat(2) + "()".repeat(10));

const tokens = processor.tokens;
const input = processor.input;

let index = 0;
let lastIndex = 0;
const max = tokens.length - 1;
const delay = 200;

const go = () => {
    // This will render the initial input, but it will invert the actual match text
    // Like this
    // 0: [Hello] Nice World
    // 1: Hello [Nice] World
    // 2: Hello Nice [World]

    const color = "\x1b[48;4;39m\x1b[38;5;0m";
    const colorDone = "\x1b[48;5;27m\x1b[38;5;255m";
    const reset = "\x1b[0m";

    const current = `${color}${tokens[index].value}${reset}`;

    const beforeCurrentTokens: Token<TokenTypes>[] = tokens.slice(0, index);
    const afterCurrentTokens: Token<TokenTypes>[] = tokens.slice(index + 1);

    const beforeValue = colorDone + beforeCurrentTokens.map(token => token.value).join("");
    const afterValue = afterCurrentTokens.map(token => token.value).join("");

    console.clear();
    console.log(`${beforeValue}${current}${afterValue}`);

    const maxLen = tokens[tokens.length - 1].end;
    const progress = `[${index}/${max}] ${(index / max) * 100}%`;

    const differenceProgress = `Diff [${(((index / max) * 100) - ((lastIndex / max) * 100)).toFixed(2)}%]`;

    console.log(progress);
    console.log(differenceProgress);

    const totalProcessedCharLength = beforeValue.length + tokens[index].value.length;

    console.log(`Exact Percent: ${(totalProcessedCharLength / maxLen) * 100}%`)

    lastIndex = index;
    index++;

    if (index > max) {
        return;
    }

    if (delay == 0)
        go();
    else
        setTimeout(go, delay);
}

go();