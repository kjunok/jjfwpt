<template>
    <codemirror  v-model="code" :options="cmOption"/>
</template>

<script>
  import dedent from 'dedent'
  import { codemirror } from 'vue-codemirror'
  // language
  import 'codemirror/mode/javascript/javascript.js'
  // theme css
  import 'codemirror/theme/pastel-on-dark.css'
  // require active-line.js
  import'codemirror/addon/selection/active-line.js'
  // styleSelectedText
  import'codemirror/addon/selection/mark-selection.js'
  import'codemirror/addon/search/searchcursor.js'
  // hint
  import'codemirror/addon/hint/show-hint.js'
  import'codemirror/addon/hint/show-hint.css'
  import'codemirror/addon/hint/javascript-hint.js'
  import'codemirror/addon/selection/active-line.js'
  // highlightSelectionMatches
  import'codemirror/addon/scroll/annotatescrollbar.js'
  import'codemirror/addon/search/matchesonscrollbar.js'
  import'codemirror/addon/search/searchcursor.js'
  import'codemirror/addon/search/match-highlighter.js'
  // keyMap
  import'codemirror/mode/clike/clike.js'
  import'codemirror/addon/edit/matchbrackets.js'
  import'codemirror/addon/comment/comment.js'
  import'codemirror/addon/dialog/dialog.js'
  import'codemirror/addon/dialog/dialog.css'
  import'codemirror/addon/search/searchcursor.js'
  import'codemirror/addon/search/search.js'
  import'codemirror/keymap/sublime.js'
  // foldGutter
  import'codemirror/addon/fold/foldgutter.css'
  import'codemirror/addon/fold/brace-fold.js'
  import'codemirror/addon/fold/comment-fold.js'
  import'codemirror/addon/fold/foldcode.js'
  import'codemirror/addon/fold/foldgutter.js'
  import'codemirror/addon/fold/indent-fold.js'
  import'codemirror/addon/fold/markdown-fold.js'
  import'codemirror/addon/fold/xml-fold.js'

  export default {
    name: 'codemirror-example-javascript',
    title: 'Mode: text/javascript & Theme: monokai',
    components: {
      codemirror
    },
    data() {
      return {
        code: dedent`
          import someResource from 'codemirror/some-resource'
          export default {
            data () {
              // 这是一个包含、代码提示、折叠、选中、sublime模式...的编辑器
              // 按下Ctrl键可以体验代码提示
              // 可以尝试sublime下的快捷键操作
              return {
                exampleCode: 'const a = 10',
                cmOption: {
                  tabSize: 4,
                  styleActiveLine: true,
                  lineNumbers: true,
                  line: true,
                  mode: 'text/javascript',
                  lineWrapping: true,
                  theme: 'default'
                }
              }
            },
            components: examples
          }
        `,
        themes:["3024-day","3024-night","abcdef","ambiance","ambiance-mobile","ayu-dark","ayu-mirage","base16-dark","base16-light","bespin","blackboard","cobalt","colorforth","darcula","dracula","duotone-dark","duotone-light","eclipse","elegant","erlang-dark","gruvbox-dark","hopscotch","icecoder","idea","isotope","lesser-dark","liquibyte","lucario","material","material-darker","material-ocean","material-palenight","mbo","mdn-like","midnight","monokai","moxer","neat","neo","night","nord","oceanic-next","panda-syntax","paraiso-dark","paraiso-light","pastel-on-dark","railscasts","rubyblue","seti","shadowfox","solarized","ssms","the-matrix","tomorrow-night-bright","tomorrow-night-eighties","ttcn","twilight","vibrant-ink","xq-dark","xq-light","yeti","yonce","zenburn"],
        theme:"pastel-on-dark",
        cmOption: {
          theme: "pastel-on-dark",
          tabSize: 4,
          styleActiveLine: false,
          lineNumbers: true,
          styleSelectedText: false,
          line: true,
          foldGutter: true,
          gutters: ["CodeMirror-linenumbers", "CodeMirror-foldgutter"],
          highlightSelectionMatches: { showToken: /\w/, annotateScrollbar: true },
          mode: 'text/javascript',
          // hint.js options
          hintOptions:{
            // 当匹配只有一项的时候是否自动补全
            completeSingle: false
          },
          //快捷键 可提供三种模式 sublime、emacs、vim
          keyMap: "sublime",
          matchBrackets: true,
          showCursorWhenSelecting: true,
          extraKeys: { "Ctrl": "autocomplete" }
        }
      }
    },
    methods:{
      getTheme(theme){
        return this.cmOption.theme=theme;
      }
    },
    mounted() {
      setTimeout(() => {
        this.styleSelectedText = true,
          this.cmOption.styleActiveLine = true
      }, 1800)
    }
  }
</script>

<style lang="stylus">
  .CodeMirror
    font-size: 18px;
    font-family: Courier New;
    font-weight: 600;
    height 700px
  .cm-s-pastel-on-dark
    span.cm-comment
      color: #577960
      font-size 15px
      font-style italic
  .cm-s-pastel-on-dark span.cm-keyword
     color: #597cc2;
  .cm-s-pastel-on-dark span.cm-property
     color: #6c97c2;
  .cm-s-pastel-on-dark span.cm-def
     color: #19be6b;
  .cm-s-pastel-on-dark span.cm-string
     color: #4d7960;
</style>
