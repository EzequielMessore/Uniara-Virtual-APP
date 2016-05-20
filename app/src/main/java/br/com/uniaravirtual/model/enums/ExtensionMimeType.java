package br.com.uniaravirtual.model.enums;

public enum ExtensionMimeType {

    DOC("application/msword"),
    DOT("application/msword"),
    DOCX("application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    DOTX("application/vnd.openxmlformats-officedocument.wordprocessingml.template"),
    DOCM("application/vnd.ms-word.document.macroEnabled.12"),
    DOTM("application/vnd.ms-word.template.macroEnabled.12;"),
    XLS("application/vnd.ms-excel"),
    XLT("application/vnd.ms-excel"),
    XLA("application/vnd.ms-excel"),
    XLSX("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    XLTX("application/vnd.openxmlformats-officedocument.spreadsheetml.template"),
    XLSM("application/vnd.ms-excel.sheet.macroEnabled.12"),
    XLTM("application/vnd.ms-excel.template.macroEnabled.12"),
    XLAM("application/vnd.ms-excel.addin.macroEnabled.12"),
    XLSB("application/vnd.ms-excel.sheet.binary.macroEnabled.12"),
    PDF("application/pdf"),
    PPT("application/vnd.ms-powerpoint"),
    POT("application/vnd.ms-powerpoint"),
    PPS("application/vnd.ms-powerpoint"),
    PPA("application/vnd.ms-powerpoint"),
    PPTX("application/vnd.openxmlformats-officedocument.presentationml.presentation"),
    POTX("application/vnd.openxmlformats-officedocument.presentationml.template"),
    PPSX("application/vnd.openxmlformats-officedocument.presentationml.slideshow"),
    PPAM("application/vnd.ms-powerpoint.addin.macroEnabled.12"),
    PPTM("application/vnd.ms-powerpoint.presentation.macroEnabled.12"),
    POTM("application/vnd.ms-powerpoint.presentation.macroEnabled.12"),
    PPSM("application/vnd.ms-powerpoint.slideshow.macroEnabled.12"),
    ZIP("application/zip");

    private String value;

    ExtensionMimeType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ExtensionMimeType get(final String name) {
        for (ExtensionMimeType mimeType : values()) {
            if (mimeType.name().equals(name.toUpperCase())) {
                return mimeType;
            }
        }
        return null;
    }

}
