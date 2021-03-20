package utilities;

import java.util.HashMap;
import java.util.Map;

public class AddPupilAPIBodyUtil {

    public static Map<String, Object> addPupilMandatoryBodyInteger(String firstName, String lastName, int gradeId) {

        Map<String, Object> addPupilBody = new HashMap<>();
        addPupilBody.put("firstName",firstName);
        addPupilBody.put("lastName",lastName);
        addPupilBody.put("gradeId",gradeId);

        return addPupilBody;
    }

    public static Map<String, Object> addPupilAllBodyInteger(String firstName, String lastName, int gradeId, String infix, int classId, String email, boolean isDisabled) {

        Map<String, Object> addPupilBody = new HashMap<>();
        addPupilBody.put("firstName",firstName);
        addPupilBody.put("lastName",lastName);
        addPupilBody.put("gradeId",gradeId);
        addPupilBody.put("infix",infix);
        addPupilBody.put("classId",classId);
        addPupilBody.put("email",email);
        addPupilBody.put("isDisabled",isDisabled);

        return addPupilBody;
    }
}
