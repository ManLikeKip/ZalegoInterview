package com.kipropkorir.zalego.gallery;

import java.util.Vector;

public interface OnPhoneImagesObtained {

    void onComplete(Vector<PhoneAlbum> albums);
    void onError();

}