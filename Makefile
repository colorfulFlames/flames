    BASEDIR=$(CURDIR)
    OUTPUTDIR=$(BASEDIR)/docs # you may change docs with custom folder name
    PACKAGE=com.severalcircles.flames # write the package name here

    html:
        javadoc "$(PACKAGE)" -d "$(OUTPUTDIR)" -encoding UTF-8

    .PHONY: html
