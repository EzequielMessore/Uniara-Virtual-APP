package br.com.uniaravirtual.util;

import android.os.Bundle;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import br.com.uniaravirtual.model.entity.Absence;
import br.com.uniaravirtual.model.entity.Grades;
import br.com.uniaravirtual.model.entity.StudentFiles;
import br.com.uniaravirtual.model.enums.BroadcastMessageType;

/**
 * Created by Ezequiel Messore on 25/04/16.
 * ezequielmessore.developer@gmail.com
 */
public class BundleFactory {

    public static Bundle makeBundleForBroadcast(final String messageType, final BroadcastMessageType
            type) {
        final Bundle bundle = new Bundle();
        bundle.putString(Keys.BROADCAST_MESSAGE.toString(), messageType);
        bundle.putString(Keys.BROADCAST_MESSAGE_TYPE.toString(), type.name());
        return bundle;
    }

    public static Bundle makeBundleForAction(final String action) {
        final Bundle bundle = new Bundle();
        bundle.putString(Keys.BROADCAST_ACTION.toString(), action);
        return bundle;
    }

    public static String getBroadcastActionBundle(final Bundle bundle) {
        return bundle.getString(Keys.BROADCAST_ACTION.toString());
    }

    public static BroadcastMessageType getBroadcastMessageType(final Bundle bundle) {
        return BroadcastMessageType.valueOf(bundle.getString(Keys.BROADCAST_MESSAGE_TYPE.toString()));
    }

    public static String getBroadcastMessage(final Bundle bundle) {
        return bundle.getString(Keys.BROADCAST_MESSAGE.toString());
    }

    public static Bundle makeBundleForListAbsences(final List<Absence> absences) {
        final Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Keys.ABSENCES.toString(), (ArrayList<? extends Parcelable>) absences);
        return bundle;
    }

    public static List<Absence> getListAbsencesFromBundle(final Bundle bundle) {
        return bundle.getParcelableArrayList(Keys.ABSENCES.toString());
    }

    public static Bundle makeBundleForListGrades(final List<Grades> absences) {
        final Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Keys.GRADES.toString(), (ArrayList<? extends Parcelable>)
                absences);
        return bundle;
    }

    public static List<Grades> getListGradesFromBundle(final Bundle bundle) {
        return bundle.getParcelableArrayList(Keys.GRADES.toString());
    }

    public static Bundle makeBundleForListStudentFiles(final List<StudentFiles> studentFiles) {
        final Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Keys.STUDENT_FILES.toString(), (ArrayList<? extends Parcelable>)
                studentFiles);
        return bundle;
    }

    public static List<StudentFiles> getListStudentFilesFromBundle(final Bundle bundle) {
        return bundle.getParcelableArrayList(Keys.STUDENT_FILES.toString());
    }

    public enum Keys {
        BROADCAST_ACTION("BROADCAST_ACTION"),
        BROADCAST_MESSAGE("BROADCAST_MESSAGE"),
        BROADCAST_MESSAGE_TYPE("BROADCAST_MESSAGE_TYPE"),
        ABSENCES("ABSENCES"),
        GRADES("GRADES"),
        STUDENT_FILES("STUDENT_FILES"),
        UPDATE_ABSENCES("UPDATE_ABSENCES"),
        UPDATE_STUDENT_FILES("UPDATE_STUDENT_FILES"),
        UPDATE_GRADES("UPDATE_GRADES");

        private String key;

        Keys(String key) {
            this.key = key;
        }

        @Override
        public String toString() {
            return this.key;
        }
    }
}
