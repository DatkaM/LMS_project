package peaksoft.dto.search;

import lombok.Getter;
import lombok.Setter;
import peaksoft.dto.response.UserResponse;

import java.util.List;

@Getter
@Setter
public class UserResponseView {

    private List<UserResponse> userResponses;
}
