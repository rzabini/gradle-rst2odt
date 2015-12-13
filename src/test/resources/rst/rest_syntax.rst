.. _rst_tutorial:



##############################################
Restructured Text (reST) and Sphinx CheatSheet
##############################################

.. topic:: Overview

    This page describes some of the RST and Sphinx syntax. It is based on resource found at `Sphinx <http://sphinx.pocoo.org/rest.html>`_ , `Docutils <http://docutils.sourceforge.net/rst.html>`_ and more generally software documentation written with Sphinx. 


    This is not an exhaustive description but it should allow you to start and create already nice documentation.


    :Date: |date|
    :Author: **Thomas Cokelaer**


.. contents:: 
    :depth: 3


Introduction
#############

The reStructuredText (RST) syntax provides an easy-to-read, what-you-see-is-what-you-get plaintext markup syntax and parser system. However, you need to be very precise and stick to some strict rules: 

    * like Python, RST syntax is sensitive to indentation !
    * RST requires blank lines between paragraphs

This entire document is written with the RST syntax. In the right sidebar, you should find a link **show source**, which shows the RST source code.

Text Formatting
#################

Inline markup and special characters (e.g., bold, italic, verbatim)
====================================================================

There are a few special characters used to format text. The special character ``*`` is used to defined bold and italic text as shown in the table below. The backquote character ````` is another special character used to create links to internal or external web pages as you will see in section `Internal and External Links`_.

=========== ================================== ==============================
usage          syntax                           HTML rendering
=========== ================================== ==============================
italic      `*italic*`                         *italic*
bold        `**bold**`                         **bold**
link        ```python <www.python.org>`_``     `python <www.python.org>`_
verbatim    ````*````                               ``*``
=========== ================================== ==============================

The double backquote is used to enter in verbatim mode, which can be used as the escaping character.
There are some restrictions about the ``*`` and `````` syntax. They

    * cannot not be nested,
    * content may not start or end with whitespace: ``* text*`` is wrong,
    * it must be separated from surrounding text by non-word characters like a space.

The use of backslash is a work around to second previous restrictions about whitespaces in the following case:

    * ``this is a *longish* paragraph`` is correct and gives *longish*.
    * ``this is a long*ish* paragraph`` is not interpreted as expected. You 
      should use ``this is a long\ *ish* paragraph`` to obtain long\ *ish* paragraph


In Python docstrings it will be necessary to escape any backslash characters so that they actually reach reStructuredText. The simplest way to do this is to use raw strings by adding the letter ``r`` in front of the docstring. 

===================================== ================================
Python string                         Typical result
===================================== ================================
``r"""\*escape* \`with` "\\""""``     ``*escape* `with` "\"``
``"""\\*escape* \\`with` "\\\\""""``  ``*escape* `with` "\"``
``"""\*escape* \`with` "\\""""``      ``escape with ""``
===================================== ================================


Headings 
==========

In order to write a title, you can either underline it or under and overline it. The following examples are correct titles. 

.. code-block:: rest

    *****
    Title
    *****

    subtitle
    ########

    subsubtitle
    **********************
    and so on

Two rules: 

  * If under and overline are used, their length must be identical
  * The length of the underline must be at least as long as the title itself

Normally, there are no heading levels assigned to certain characters as the 
structure is determined from the succession of headings. However, it is better to stick to the same convention throughout a project. For instance: 

* `#` with overline, for parts
* `*` with overline, for chapters
* `=`, for sections
* `-`, for subsections
* `^`, for subsubsections
* `"`, for paragraphs


Internal and External Links
=============================

In Sphinx, you have 3 type of links:
    #. External links (http-like)
    #. Implicit links to title
    #. Explicit links to user-defined label (e.g., to refer to external titles).


External links
----------------

If you want to create a link to a website, the syntax is ::

    `<http://www.python.org/>`_

which appear as `<http://www.python.org/>`_ . Note the underscore after the final single quote. Since the full name of the link is not always simple or meaningful, you can specify a label (note the space between the label and link name)::

    `Python <http://www.python.org/>`_

The rendering is now: `Python <http://www.python.org/>`_. 

.. note:: If you have an underscore within the label/name, you got to escape it with a '\\' character.


.. _implicit:

Implicit Links to Titles
------------------------------

All titles are considered as hyperlinks. A link to a title is just its name within quotes and a final underscore::

    `Internal and External links`_

This syntax works only if the title and link are within the same RST file.
If this is not the case, then you need to create a label before the title and refer to this new link explicitly, as explained in `Explicit Links`_ section.

Explicit Links
--------------------

You can create explicit links within your RST files. For instance, this document has a label at the top called ``rst_tutorial``, which is specified by typing::

    .. _rst_tutorial:

You can refer to this label using two different methods. The first one is::

    rst_tutorial_

List and bullets
================

The following code::

    * This is a bulleted list.
    * It has two items, the second
      item uses two lines. (note the indentation)

    1. This is a numbered list.
    2. It has two items too.

    #. This is a numbered list.
    #. It has two items too.

gives:

* This is a bulleted list.
* It has two items, the second
  item uses two lines. (note the indentation)

1. This is a numbered list.
2. It has two items too.

#. This is a numbered list.
#. It has two items too.

.. note:: if two lists are separated by a blanck line only, then the two lists are not differentiated as you can see above.


What are directives
############################

Sphinx and the RST syntax provides directives to include formatted text. As an example, let us consider the **code-block** syntax. It allows to insert code (here HTML) within your document::

    .. code-block:: html
        :linenos:

        <h1>code block example</h1>

Its rendering is:

.. code-block:: html
..    :linenos:

     <h1>code block example</h1>

Here, **code-block** is the name of the directive. **html** is an argument telling that the code is in HTML format, **lineos** is an option telling to insert line number and finally after a blank line is the text to include.

Note that options are tabulated.

Inserting code and Literal blocks
#######################################

How to include simple code
===================================

This easiest way to insert literal code blocks is to end a paragraph with the special marker made of a double coulumn `::`. Then, the literal block must be indented:: 

    This is a simple example::

        import math
        print 'import done'
    
or::

    This is a simple example:
    ::

        import math
        print 'import done'

gives:

This is a simple example::

    import math
    print 'import done' 


code-block directive
===================================

By default the syntax of the language is Python, but you can specify the language using the **code-block** directive as follows::

    .. code-block:: html
       :linenos:

       <h1>code block example</h1>

produces

.. code-block:: html
..    :linenos:

    <h1>code block example</h1>

Include code with the literalinclude directive
======================================================

Then, it is also possible to include the contents of a file as follows:

.. code-block:: rest

    .. literalinclude:: filename
        :linenos:
        :language: python
        :lines: 1, 3-5
        :start-after: 3
        :end-before: 5

For instance, the ``sample.py`` file contents can be printed:

Tables
######

There are several ways to write tables. Use standard reStructuredText tables as explained here. They work fine in HTML output, however, there are some gotchas when using tables for LaTeX output.

The rendering of the table depends on the CSS/HTML style, not on sphinx itself.


Simple tables
================


Simple tables can be written as follows::

    +---------+---------+-----------+
    | 1       |  2      |  3        |
    +---------+---------+-----------+

which gives:

+---------+---------+-----------+
| 1       | 2       | 3         |
+---------+---------+-----------+

Size of the cells can be adjusted as follows::

    +---------------------+---------+---+
    |1                    |        2| 3 |
    +---------------------+---------+---+

renders as follows:

+---------------------+---------+---+
|1                    |        2| 3 |
+---------------------+---------+---+

This syntax is quite limited, especially for multi cells/columns.


Multicells tables, first method
====================================
A first method is the following syntax::

        +------------+------------+-----------+
        | Header 1   | Header 2   | Header 3  |
        +============+============+===========+
        | body row 1 | column 2   | column 3  |
        +------------+------------+-----------+
        | body row 2 | Cells may span columns.|
        +------------+------------+-----------+
        | body row 3 | Cells may  | - Cells   |
        +------------+ span rows. | - contain |
        | body row 4 |            | - blocks. |
        +------------+------------+-----------+

gives:

.. htmlonly::

    +------------+------------+-----------+
    | Header 1   | Header 2   | Header 3  |
    +============+============+===========+
    | body row 1 | column 2   | column 3  |
    +------------+------------+-----------+
    | body row 2 | Cells may span columns.|
    +------------+------------+-----------+
    | body row 3 | Cells may  | - Cells   |
    +------------+ span rows. | - contain |
    | body row 4 |            | - blocks. |
    +------------+------------+-----------+

Multicells table, second method
====================================
The previous syntax can be simplified::

    =====  =====  ======
       Inputs     Output
    ------------  ------
      A      B    A or B
    =====  =====  ======
    False  False  False
    True   False  True
    =====  =====  ======

gives:

.. htmlonly::

    =====  =====  ======
       Inputs     Output
    ------------  ------
      A      B    A or B
    =====  =====  ======
    False  False  False
    True   False  True
    =====  =====  ======

.. note:: table and latex documents are not yet compatible in sphinx, and you should therefore precede them with the a special directive (.. htmlonly::)

The tabularcolumns directive
=================================

The previous examples work fine in HTML output, however there are some gotchas when using tables in LaTeX: the column width is hard to determine correctly automatically. For this reason, the following directive exists::

    .. tabularcolumns:: column spec

This directive gives a â€œcolumn specâ€ for the next table occurring in the source file. It can have values like::

    |l|l|l|

which means three left-adjusted (LaTeX syntax). By default, Sphinx uses a table layout with L for every column. This code::

    .. tabularcolumns:: |l|c|p{5cm}|

    +--------------+---+-----------+
    |  simple text | 2 | 3         |
    +--------------+---+-----------+

gives 

.. htmlonly::

    .. tabularcolumns:: |l|c|p{5cm}|

    +--------------+------------+-----------+
    | title        |            |           |
    +==============+============+===========+
    |  simple text | 2          | 3         |
    +--------------+------------+-----------+

The csv-table directive
==========================================
Finally, a convenient way to create table is the usage of CSV-like syntax::


    .. csv-table:: a title
       :header: "name", "firstname", "age"
       :widths: 20, 20, 10

       "Smith", "John", 40
       "Smith", "John, Junior", 20

that is rendered as follows:


.. csv-table:: a title
   :header: "name", "firstname", "age"
   :widths: 20, 20, 10

   "Smith", "John", 40
   "Smith", "John, Junior", 20

Images and figures
#######################

Include Images
===============

Use::

    .. image:: stars.jpg
        :width: 200px
        :align: center
        :height: 100px
        :alt: alternate text

to put an image

.. image:: stars.jpg
    :width: 200px
    :align: center
    :height: 100px
    :alt: alternate text

Include a Figure
=================

::

    .. figure:: stars.jpg
        :width: 200px
        :align: center
        :height: 100px
        :alt: alternate text
        :figclass: align-center

        figure are like images but with a caption

        and whatever else youwish to add
    
        .. code-block:: python

            import image 


gives

.. figure:: stars.jpg
    :width: 200px
    :align: center
    :height: 100px
    :alt: alternate text
    :figclass: align-center

    figure are like images but with a caption

    and whatever else youwish to add
    
    .. code-block:: python

        import image 

The option **figclass** is a CSS class that can be tuned for the final HTML rendering.


Boxes
#################

Colored boxes: note, seealso, todo and warnings
=================================================

There are simple directives like **seealso** that creates nice colored boxes:

.. seealso:: This is a simple **seealso** note. 

created using::

    .. seealso:: This is a simple **seealso** note. 

You have also the **note** directive:

.. note::  This is a **note** box.

with ::

    .. note::  This is a **note** box.

and the warning directive:

.. warning:: note the space between the directive and the text

generated with::

    .. warning:: note the space between the directive and the text


There is another  **todo** directive but requires an extension. See 
`Useful extensions`_


Topic directive
===============
A **Topic** directive  allows to write a title and a text together within a box similarly to the **note** directive.

This code::

    .. topic:: Your Topic Title

        Subsequent indented lines comprise
        the body of the topic, and are
        interpreted as body elements.

gives

.. topic:: Your Topic Title

    Subsequent indented lines comprise
    the body of the topic, and are
    interpreted as body elements.

Sidebar directive
=================

It is possible to create sidebar using the following code::

    .. sidebar:: Sidebar Title
        :subtitle: Optional Sidebar Subtitle

        Subsequent indented lines comprise
        the body of the sidebar, and are
        interpreted as body elements.


.. sidebar:: Sidebar Title
    :subtitle: Optional Sidebar Subtitle

    Subsequent indented lines comprise
    the body of the sidebar, and are
    interpreted as body elements.






