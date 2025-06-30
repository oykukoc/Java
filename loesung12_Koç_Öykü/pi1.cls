% -----------------------------------------------------------------------
% Based on pi1-muster.tex and defs.tex used before 2003 for
% PI 1 in Bremen.
% Adapted by Thomas Röfer and Malte Kuhn (uni@monkz.de)
% Further adapted by Sebastian Offermann, Florian Pflüger and Ferdinand Rohlfing.

\NeedsTeXFormat{LaTeX2e}
\ProvidesClass{pi1}

\LoadClass[11pt,a4paper]{article}

% -----------------------------------------------------------------------
% Packages

\usepackage[utf8]{inputenc} % Input encoding
\usepackage[T1]{fontenc} % Font encoding
\usepackage[ngerman]{babel} % German language settings
\usepackage{graphicx} % Include graphics
\usepackage{listings} % Include code
\usepackage{fancyhdr} % Customize header and footer
\usepackage{color} % Colors
\usepackage{geometry} % Customize page margins
\usepackage{tocloft} % Customize table of contents
% Links
\usepackage[
  hidelinks, % remove color and borders
  breaklinks = true,
  colorlinks = true,
  allcolors = blue,
  % linkcolor = blue,
  % citecolor = blue,
  % urlcolor = blue,
  ]{hyperref}

% -----------------------------------------------------------------------
% Information
\newcommand{\thissemester}{WiSe 2023/24}
\newcommand{\thistitle}{Praktische Informatik}
\newcommand{\thisshorttitle}{PI}
\newcommand{\thismodulnumber}{1}

% -----------------------------------------------------------------------
% Page Style and Layout

\pagestyle{fancy}
\renewcommand{\headwidth}{\linewidth} % header full line width
% Margins
\geometry{
  a4paper,
  left=2.5cm,
  top=2.5cm,
  right=2.5cm,
  bottom=2.5cm,
  headsep=1cm,
  footnotesep=1cm,
}
\setlength{\parskip}{1ex} % space between paragraphs
\setlength{\parindent}{0ex} % indent after paragraph
\sloppy % don't write past right margin

% -----------------------------------------------------------------------
% Colors for Syntax Highlighting
\definecolor{jred}{rgb}{0.6,0,0}
\definecolor{jgreen}{RGB}{63,127,95} % comments
\definecolor{jpurple}{RGB}{127,0,85} % keywords
\definecolor{jblue}{RGB}{42,0,255} % strings
\definecolor{jlightblue}{RGB}{63,95,191} % multiline comments
\definecolor{jgrey}{rgb}{0.99,0.99,0.95} % background

% -----------------------------------------------------------------------
% Java Syntax Highlighting
\lstloadlanguages{Java}
\lstset{
  language = Java,
  % syntax highlighting
  basicstyle = \ttfamily\scriptsize,
  numberstyle = \tiny,
  keywordstyle = \bfseries\color{jpurple},
  stringstyle = \color{jblue}\ttfamily,
  commentstyle = \color{jgreen}\ttfamily,
  morecomment = [s][\color{jlightblue}]{/**}{*/},
  %backgroundcolor = \color{jgrey},
  % general
  frame = none,
  stepnumber = 1,
  % layout
  numbers = left,
  numbersep = 5pt,
  tabsize = 4,
  %abovecaptionskip = 0cm,
  %belowcaptionskip = 0cm,
  % break on long lines
  breakindent = 41pt,
  breaklines = true,
  %breakatwhitespace = true,
  % show invisible characters
  showspaces = false,
  showtabs = false,
  showstringspaces = false,
  % allows extended characters
  literate = {
    % https://en.wikibooks.org/wiki/LaTeX/Source_Code_Listings#Encoding_issue
    {á}{{\'a}}1 {é}{{\'e}}1 {í}{{\'i}}1 {ó}{{\'o}}1 {ú}{{\'u}}1
    {Á}{{\'A}}1 {É}{{\'E}}1 {Í}{{\'I}}1 {Ó}{{\'O}}1 {Ú}{{\'U}}1
    {à}{{\`a}}1 {è}{{\`e}}1 {ì}{{\`i}}1 {ò}{{\`o}}1 {ù}{{\`u}}1
    {À}{{\`A}}1 {È}{{\'E}}1 {Ì}{{\`I}}1 {Ò}{{\`O}}1 {Ù}{{\`U}}1
    {ä}{{\"a}}1 {ë}{{\"e}}1 {ï}{{\"i}}1 {ö}{{\"o}}1 {ü}{{\"u}}1
    {Ä}{{\"A}}1 {Ë}{{\"E}}1 {Ï}{{\"I}}1 {Ö}{{\"O}}1 {Ü}{{\"U}}1
    {â}{{\^a}}1 {ê}{{\^e}}1 {î}{{\^i}}1 {ô}{{\^o}}1 {û}{{\^u}}1
    {Â}{{\^A}}1 {Ê}{{\^E}}1 {Î}{{\^I}}1 {Ô}{{\^O}}1 {Û}{{\^U}}1
    {ã}{{\~a}}1 {ẽ}{{\~e}}1 {ĩ}{{\~i}}1 {õ}{{\~o}}1 {ũ}{{\~u}}1
    {Ã}{{\~A}}1 {Ẽ}{{\~E}}1 {Ĩ}{{\~I}}1 {Õ}{{\~O}}1 {Ũ}{{\~U}}1
    {œ}{{\oe}}1 {Œ}{{\OE}}1 {æ}{{\ae}}1 {Æ}{{\AE}}1 {ß}{{\ss}}1
    {ű}{{\H{u}}}1 {Ű}{{\H{U}}}1 {ő}{{\H{o}}}1 {Ő}{{\H{O}}}1
    {ç}{{\c c}}1 {Ç}{{\c C}}1 {ø}{{\o}}1 {å}{{\r a}}1 {Å}{{\r A}}1
    {€}{{\euro}}1 {£}{{\pounds}}1 {«}{{\guillemotleft}}1
    {»}{{\guillemotright}}1 {ñ}{{\~n}}1 {Ñ}{{\~N}}1 {¿}{{?`}}1 {¡}{{!`}}1
    % other symbols
    {€}{{\EUR}}1 {°}{{$^\circ$}}1
  },
  % Show list path/name
  %title={\small\lstname},
  %captionpos=b, % Position of title/caption
}

% -----------------------------------------------------------------------
% Main Macros
\renewcommand{\thesection}{Aufgabe \arabic{section}}
\newcommand{\skipsection}{\stepcounter{section}}
% customize maketitle command
\renewcommand{\maketitle}[3]{
  \newpage\null\vspace{-1.5cm}
  \thispagestyle{empty} % no header/footer on title page
  {
    \renewcommand{\arraystretch}{1.2} % spacing between table rows
    \begin{tabular}[t]{p{0.45\linewidth} @{\hskip 0.05\linewidth} p{0.45\linewidth}}
      \thistitle\ \MakeUppercase{\romannumeral \thismodulnumber} & \thissemester\\
      Tutor:in: {#2}  &  Bearbeiter:in: {#3}
    \end{tabular}
  }\\[0.5cm]
  {\Huge\sf Übungsblatt #1} \\[1.5ex]
  {\Large Abgabe: 28.01.2024}\\
  \hrule\vspace{0.5cm}

  \fancyhf{} % clears footer and header
  \fancyhead[L]{\thisshorttitle\ \thismodulnumber, \thissemester}
  \fancyhead[R]{#3 zu Übungsblatt #1}
  \fancyfoot[R]{\thepage}
}

\newlength{\seclen}
\settowidth{\seclen}{\textbf{Aufgabe }}
\newlength{\subseclen}

\settowidth{\subseclen}{Aufgabe }
\addtolength{\cftsecnumwidth}{\seclen}
\addtolength{\cftsubsecnumwidth}{\subseclen}
\addtolength{\cftsubsubsecnumwidth}{\subseclen}

% -----------------------------------------------------------------------
% Misc macros
\newcommand{\grenzop}[3]{\mathop{#1}\limits^{#2}_{#3}}
\newcommand{\langrechtspfeil}[2]{\grenzop{\longrightarrow}{#1}{#2}}
\newcommand{\bob}[0]{\hspace*{\fill}$\Box$}

\endinput