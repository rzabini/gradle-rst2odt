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

The second method use the ``ref`` role as follows::

    :ref:`rst_tutorial`

With the first method, the link appears as rst_tutorial_, whereas the second method use the first title's name found after the link. Here, the second method would appear as :ref:`rst_tutorial`. 

Note that the second method is compulsary if the link is to be found in an external RST file. For instance, the introduction page is an external page with a link called ``introduction`` at the top of the page. You can jump there by writing ``:ref:`introduction```, which appears as: :ref:`introduction`.


.. note:: Note that if you use the ``ref`` role, the final underscore is not required anymore.


