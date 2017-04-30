
Pointer Analysis


\date{15/02/2016}

\maketitle

\section{Introduction}
A pointer analysis attempts to statically determine the possible run-time value of a pointer.A pointer alias analysis attempts to determine when two pointer expressions refer to the same storage location. \cite{Hind:2001:PAH:379605.379665}\\
In this project, we try to implement Anderson's Algorithm for Points-to Analysis\\
The Basic algorithm for C is like following:\\
\begin{enumerate}
    \item  $x=\&y$  | $y\in pt(x)$
    \item  $x = y$  | $pt(y)\subseteq pt(x)$

    \item  $x = *y$ | $\forall a\in pt(y).pt(a)\subseteq pt(x)$ 

    \item  $*x = y$ | $\forall w\in pt(x).pt(y)\subseteq pt(w)$

\end{enumerate}

Because in Java we don't have item 1,3,4 we need to implement item 2 which is Refrence to Refrence in Java. 

\iffalse
% code from http://rosettacode.org/wiki/Fibonacci_sequence#Python
\begin{lstlisting}[label={list:first},caption=Sample Python code -- Fibonacci sequence calculated analytically.]
from math import *

# define function 
def analytic_fibonacci(n):
  sqrt_5 = sqrt(5);
  p = (1 + sqrt_5) / 2;
  q = 1/p;
  return int( (p**n + q**n) / sqrt_5 + 0.5 )
 
# define range
for i in range(1,31):
  print analytic_fibonacci(i)
\end{lstlisting}

Following Listing~\ref{list:first}\ldots{} 
Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
\fi
\pagebreak
\section{Algorithm}

This Implementation is \textbf{flow-insensitive} and \textbf{context-insensitive}. \\
Based on \cite{Sridharan2009}, \textbf{Field-Sensitivity} added to this project. \\
Field Sensitivity: Based on Anderson Algorithm, add object reference fields to points-to graph as suffices for reference variables. For example: class A has fields f,g then $p=new A()$, means p.f and p.g are in the points-to graph\cite{Rountev:2001:PAJ:504311.504286}\\
\includegraphics[scale=0.6]{1}\\
This implementation is \textbf{Subset-based}: The analysis models directionality of assignments; i.e., a statement $x = y$ implies $pt(y) \subseteq pt(x)$.\\
Implemented field-sensitivity is like bellow:\\
\begin{enumerate}
\item  $x = y$| $pt(y)\subseteq pt(x)$
\item  $y.f = x$ | $\forall o\in pt(y)(pt(x)\subseteq pt(o.f)) $
\item $x = y.f$ |$\forall o\in pt(y)(o.f\subseteq pt(x))$
\end{enumerate}
\pagebreak
The abstract idea of implementation part is like following:\\
\begin{lstlisting}[label={list:second},caption=Implemented Anderson Algorithm]
initialize graph and PointsTo set
W={v|ptr(v)!=empty}
while (!W.isempty)
    v=W.remove(0);
    foreach edge new->q 
        ptr(q)=ptr(q)+newID; 
        add q to w
    foreach edge v->q 
        ptr(q)=ptr(q)+ptr(v);
        add q to w if q changed
    foreach edge v.f->q
        ptr(q)=ptr(q)+ptr(v.f);
        add q to w if q changed
    foreach edge v->q.f
        ptr(q.f)=ptr(q.f)+ptr(v);
        add q.f to w if q.f changed

\end{lstlisting}

\iffalse
\begin{lstlisting}[label={list:second},caption=Sample Bash code.]
#! /bin/bash
python stage1.py
echo "Stage I done!"
python stage2.py
echo "Stage II done!"
python stage3.py
echo "Stage III done!"
\end{lstlisting}

Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
\fi
\pagebreak

\section{How to RUN}
For Running this project, we need to put TestCase in /src/TestCase directory and in StandaloneMain.java 
\begin{lstlisting}[label={list:first},caption=StandaloneMain.java .]
    //Change to Name of Class
    //TestCase is name of package
		String ClassName = "TestCase.Elevator";
	//Change to name of file which we want to analyse
        String LocationoftestFile = "src\\TestCase\\Elevator.java";

\end{lstlisting}
After running successfully, two output file will created in main directory of project which is the final result based on problem definition.\\
\section{Results}
The result is consist of two file which named output1.txt and output2.txt\\
Output1.txt: Consists of Line of Code, performance, Average set of points-to set for each method\\
output2.txt: File name, Line number, Variable, Points-to set\\
Because my program is context-insensitive, it needs to set that we want to analyse which method of my program, I create a loop, so it will iterate over all methods in the program which is given.Thus,I add name of method-call for Output1 and Output2.txt is also consist several line related to each method.

\section{Real-world program}
I use Software-artifact Infrastructure Repository (SIR)  for experimentation part of this project.  I download two Java projects (AlarmClock and Elevator) and run my project to find points-to based on Anderson's Algorithm with adding field sensitivity to it.\\
The repository contains many Java and C, $C++$, and $C\#$ software systems, in multiple versions, together with supporting artifacts such as test suites, fault data, and scripts.\cite{softwareartifactinfrastructurerepository2016}\\
