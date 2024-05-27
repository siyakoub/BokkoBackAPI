package com.msyconseil.bokkobackapi.service;

import com.msyconseil.bokkobackapi.dto.VehiculeDTO;
import com.msyconseil.bokkobackapi.model.SessionModel;
import com.msyconseil.bokkobackapi.model.UserModel;
import com.msyconseil.bokkobackapi.model.VehiculeModel;
import com.msyconseil.bokkobackapi.repository.UserRepository;
import com.msyconseil.bokkobackapi.repository.VehiculeRepository;
import com.msyconseil.bokkobackapi.service.customanswer.CustomAnswer;
import com.msyconseil.bokkobackapi.service.customanswer.CustomListAnswer;
import com.msyconseil.bokkobackapi.service.enumerator.ErrorMessageEnum;
import com.msyconseil.bokkobackapi.service.enumerator.VehiculeMessageEnum;
import com.msyconseil.bokkobackapi.service.exception.ErrorException;
import com.msyconseil.bokkobackapi.service.exception.VehiculeException;
import com.msyconseil.bokkobackapi.service.interf.IService;
import com.msyconseil.bokkobackapi.service.interf.ICRUDService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.LinkedList;
import java.util.Map;
import java.util.List;
import java.util.Objects;

@Service
public class VehiculeService extends AbstractService<VehiculeDTO, VehiculeModel> implements IService<VehiculeModel, VehiculeDTO>, ICRUDService<VehiculeDTO, String> {

    @Autowired
    VehiculeRepository vehiculeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    public VehiculeService(SessionService sessionService) {
        super(sessionService);
    }

    public VehiculeModel getLastVehiculeByDriver(int idConducteur) {
        return vehiculeRepository.findLastVehiculeByDriver(idConducteur);
    }

    public VehiculeModel getLastVehiculeByDriverActif(int idConducteur) {
        return vehiculeRepository.findLastVehiculeByDriverActif(idConducteur);
    }

    public VehiculeModel getVehiculeByIdActif(int idVehicule) {
        return vehiculeRepository.findByIdAndActif(idVehicule);
    }

    @Transactional
    public VehiculeDTO add(VehiculeDTO vehiculeDTO) throws ErrorException {
        if (vehiculeDTO == null) {
            throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        }
        // Convertir DTO en modèle en faisant attention à ne pas créer de nouvel utilisateur.
        VehiculeModel vehiculeModel = generateEntityByDTO(vehiculeDTO);
        vehiculeModel.setUsed(vehiculeDTO.getUsed());

        // Ici, vous devriez avoir l'ID de l'utilisateur dans vehiculeDTO
        if (vehiculeDTO.getUserDTO().getId() != null) {
            UserModel user = userRepository.findById(vehiculeDTO.getUserDTO().getId())
                    .orElseThrow(() -> new ErrorException(ErrorMessageEnum.ENTITY_NOT_EXISTS));
            vehiculeModel.setUserModel(user);
        }

        vehiculeModel = vehiculeRepository.save(vehiculeModel);
        return generateDTOByEntity(vehiculeModel);
    }

    public VehiculeDTO add(VehiculeModel vehiculeModel) throws ErrorException {
        return generateDTOByEntity(vehiculeRepository.save(vehiculeModel));
    }

    public CustomAnswer<VehiculeDTO> getById(Map<String, String> headers, String email, int id) throws ErrorException {
        if (headers == null || headers.isEmpty()) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        if (headers.get("token") == null || headers.get("token").isEmpty()) throw new ErrorException(ErrorMessageEnum.INVALID_TOKEN);
        CustomAnswer<VehiculeDTO> response = new CustomAnswer<>();
        try {
            SessionModel sessionModel = getActiveSession(headers);
            if (Objects.equals(sessionModel.getUserModel().getEmail(), email)) {
                VehiculeModel vehiculeModel = getVehiculeByIdActif(id);
                if (vehiculeModel != null) {
                    VehiculeDTO vehiculeDTO = generateDTOByEntity(vehiculeModel);
                    vehiculeDTO.setId(vehiculeModel.getId());
                    vehiculeDTO.setUsed(vehiculeModel.getUsed());
                    response.setContent(vehiculeDTO);
                } else {
                    throw new VehiculeException(VehiculeMessageEnum.NOT_FOUND);
                }
            } else {
                throw new Exception("TOKEN ou EMAIL invalid...");
            }
        } catch (Exception e) {
            e.fillInStackTrace();
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public CustomAnswer<VehiculeDTO> get(Map<String, String> headers, String email) throws ErrorException {
        if (headers == null || headers.isEmpty()) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        CustomAnswer<VehiculeDTO> response = new CustomAnswer<>();
        try {
            SessionModel sessionModel = getActiveSession(headers);
            VehiculeModel vehiculeModel = getLastVehiculeByDriverActif(sessionModel.getUserModel().getId());
            if (vehiculeModel != null) {
                VehiculeDTO vehiculeDTO = generateDTOByEntity(vehiculeModel);
                vehiculeDTO.setId(vehiculeModel.getId());
                vehiculeDTO.setUsed(vehiculeModel.getUsed());
                response.setContent(vehiculeDTO);
            } else {
                throw new VehiculeException(VehiculeMessageEnum.NOT_FOUND);
            }
        } catch (Exception e) {
            e.fillInStackTrace();
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public CustomListAnswer<List<VehiculeDTO>> getAll(Map<String, String> headers, int page, int size) throws ErrorException, VehiculeException {
        if (headers == null || headers.isEmpty()) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        CustomListAnswer<List<VehiculeDTO>> response = new CustomListAnswer<>();
        List<VehiculeDTO> list = new LinkedList<>();
        for (VehiculeModel vehiculeModel : vehiculeRepository.findAllVehiculeActif()) {
            VehiculeDTO vehiculeDTO = generateDTOByEntity(vehiculeModel);
            vehiculeDTO.setUsed(vehiculeModel.getUsed());
            list.add(vehiculeDTO);
        }
        if (list.isEmpty()) {
            throw new VehiculeException(VehiculeMessageEnum.NOT_FOUND);
        } else {
            response.setContent(list);
        }
        return response;
    }

    public CustomListAnswer<List<VehiculeDTO>> getAllByUser(final Map<String, String> headers, String email) throws ErrorException, VehiculeException {
        if (headers == null || headers.isEmpty()) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        CustomListAnswer<List<VehiculeDTO>> response = new CustomListAnswer<>();
        List<VehiculeDTO> list = new LinkedList<>();
        try {
            SessionModel sessionModel = getActiveSession(headers);
            if (Objects.equals(sessionModel.getUserModel().getEmail(), email)) {
                for (VehiculeModel vehiculeModel: vehiculeRepository.findAllVehiculeActifByDriver(sessionModel.getUserModel().getId())) {
                    list.add(generateDTOByEntity(vehiculeModel));
                }
                if (list.isEmpty()) {
                    throw new VehiculeException(VehiculeMessageEnum.NOT_FOUND);
                } else {
                    response.setContent(list);
                }
            } else {
                throw new Exception("Le token ne correspond pas à l'utilisateur...");
            }
        } catch (Exception e) {
            e.fillInStackTrace();
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public CustomAnswer<VehiculeDTO> add(Map<String, String> headers, VehiculeDTO parameter) throws ErrorException {
        if (headers == null || headers.isEmpty()) {
            throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        }
        CustomAnswer<VehiculeDTO> response = new CustomAnswer<>();
        try {
            getActiveSession(headers);
            VehiculeDTO vehiculeDTO = add(parameter);
            if (vehiculeDTO == null) {
                throw new VehiculeException(VehiculeMessageEnum.ERROR_CREATION_AUTO);
            }
            response.setContent(vehiculeDTO);
        } catch (Exception e) {
            e.fillInStackTrace();
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public CustomAnswer<VehiculeDTO> update(Map<String, String> headers, VehiculeDTO parameter, String email) throws ErrorException {
        if (headers == null || headers.isEmpty()) {
            throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        }

        CustomAnswer<VehiculeDTO> response = new CustomAnswer<>();
        try {
            getActiveSession(headers);

            VehiculeModel vehiculeModel = getVehiculeByIdActif(parameter.getId());
            if (vehiculeModel == null) {
                throw new VehiculeException(VehiculeMessageEnum.NOT_FOUND);
            }

            updateInformation(vehiculeModel, parameter);
            vehiculeModel = vehiculeRepository.save(vehiculeModel);

            VehiculeDTO vehiculeDTO = generateDTOByEntity(vehiculeModel);
            response.setContent(vehiculeDTO);

        } catch (ErrorException e) {
            response.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            e.fillInStackTrace();
            response.setErrorMessage("An unexpected error occurred: " + e.getMessage());
        }
        return response;
    }

    @Override
    public CustomAnswer<VehiculeDTO> delete(Map<String, String> headers, String email) throws ErrorException, VehiculeException {
        if (headers == null || headers.isEmpty()) throw new ErrorException(ErrorMessageEnum.ACTION_UNAUTHORISED_ERROR);
        CustomAnswer<VehiculeDTO> response = new CustomAnswer<>();
        try {
            SessionModel sessionModel = getActiveSession(headers);
            VehiculeModel vehiculeModel = getLastVehiculeByDriverActif(sessionModel.getUserModel().getId());
            if (vehiculeModel != null) {
                VehiculeDTO vehiculeDTO = generateDTOByEntity(vehiculeModel);
                response.setContent(vehiculeDTO);
                vehiculeRepository.delete(vehiculeModel);
            } else {
                throw new VehiculeException(VehiculeMessageEnum.NOT_FOUND);
            }
        } catch (VehiculeException e) {
            e.fillInStackTrace();
            response.setErrorMessage(VehiculeMessageEnum.ERROR_DELETE_AUTO.getMessage());
        } catch (Exception e) {
            e.fillInStackTrace();
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    private void updateInformation(VehiculeModel vehiculeModel, VehiculeDTO parameter) {
        vehiculeModel.setMarque(parameter.getMarque());
        vehiculeModel.setModele(parameter.getModele());
        vehiculeModel.setCouleur(parameter.getCouleur());
        vehiculeModel.setImmatriculation(parameter.getImmatriculation());
        vehiculeModel.setAnnee(parameter.getAnnee());
    }

    @Override
    public VehiculeModel generateEntityByDTO(VehiculeDTO dto) throws ErrorException {
        return mapDTOToEntity(dto);
    }

    @Override
    public VehiculeModel mapEntityByWithDTO(VehiculeModel entity, VehiculeDTO dto) throws ErrorException {
        if (entity == null) throw new ErrorException(ErrorMessageEnum.ENTITY_FABRICATION_ERROR);
        entity.setUserModel(userService.generateEntityByDTO(dto.getUserDTO()));
        entity.setModele(dto.getModele());
        entity.setMarque(dto.getMarque());
        entity.setCouleur(dto.getCouleur());
        entity.setAnnee(dto.getAnnee());
        entity.setImmatriculation(dto.getImmatriculation());
        return entity;
    }

    @Override
    public VehiculeDTO generateDTOByEntity(VehiculeModel entity) throws ErrorException {
        if (entity == null) {
            throw new ErrorException(ErrorMessageEnum.ENTITY_FABRICATION_ERROR);
        }
        VehiculeDTO dto = new VehiculeDTO();
        dto.setUserDTO(userService.generateDTOByEntity(entity.getUserModel()));
        dto.setModele(entity.getModele());
        dto.setMarque(entity.getMarque());
        dto.setAnnee(entity.getAnnee());
        dto.setCouleur(entity.getCouleur());
        dto.setImmatriculation(entity.getImmatriculation());
        dto.setId(entity.getId());
        return dto;
    }

    @Override
    public VehiculeModel mapDTOToEntity(VehiculeDTO dto) throws ErrorException {
        if (dto == null) throw new ErrorException(ErrorMessageEnum.DTO_FABRICATION_ERROR);
        VehiculeModel entity = new VehiculeModel();
        entity.setUserModel(userService.generateEntityByDTO(dto.getUserDTO()));
        entity.setMarque(dto.getMarque());
        entity.setModele(dto.getModele());
        entity.setAnnee(dto.getAnnee());
        entity.setCouleur(dto.getCouleur());
        entity.setImmatriculation(dto.getImmatriculation());
        return entity;
    }
}
