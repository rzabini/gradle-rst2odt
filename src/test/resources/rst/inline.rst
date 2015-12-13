Inline markup and special characters (e.g., bold, italic, verbatim)
====================================================================

There are a few special characters used to format text. The special character ``*`` is used to defined bold and italic text as shown in the table below. The backquote character ````` is another special character used to create links to internal or external web pages as you will see in section `Internal and External Links`_.

============ ================================== ==============================
usage        syntax                             HTML rendering
============ ================================== ==============================
italic       `*italic*`                         *italic*
bold         `**bold**`                         **bold**
link         ```python <www.python.org>`_``     `python <www.python.org>`_
verbatim     ````*````                               ``*``
============ ================================== ==============================

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
