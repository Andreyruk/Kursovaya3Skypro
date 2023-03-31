package pro.sky.kursovaya3skypro.controllers;

import pro.sky.kursovaya3skypro.enums.Colors;
import pro.sky.kursovaya3skypro.enums.EventTypes;
import pro.sky.kursovaya3skypro.enums.Sizes;
import pro.sky.kursovaya3skypro.model.Socks;
import pro.sky.kursovaya3skypro.services.SocksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * Контроллер для работы с носками
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/socks")
@Tag(name = "Носки", description = "Работа с носками")
public class SocksController {

    /**
     * Сервис для работы носками
     */
    private final SocksService socksService;

    /**
     * Ендпоинт добавления носков
     * @param color цвет {@link Colors}
     * @param size размер
     * @param cottonPart содержание хлопка
     * @param quantity количество
     * @return Идентификатор созданной пары носков
     */
    @PostMapping
    @Operation(summary = "Добавление носков",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Запись добавлена",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Socks.class))}),
                    @ApiResponse(responseCode = "500", description = "Возникли ошибки во время добавлении записи")
            })
    public int addSocks(@Parameter(description = "Цвет")
                        @RequestParam
                        Colors color,
                        @Parameter(description = "Размер")
                        @RequestParam
                        Sizes size,
                        @Parameter(description = "Процентное содержание хлопка")
                        @RequestParam
                        int cottonPart,
                        @Parameter(description = "Количество")
                        @RequestParam
                        int quantity) {
        return socksService.addSocks(color, size, cottonPart, quantity, EventTypes.ADDITION, null);
    }

    /**
     * Ендпоинт получения количества носков по параметрам
     * @param color цвет
     * @param size размер
     * @param cottonMin минимальное количество хлопка в товаре
     * @param cottonMax максимальное количество хлопка в товаре
     * @return Количество по найденным параметрам
     */
    @GetMapping
    @Operation(summary = "Получение количества носков по параметрам",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Количество получено"),
                    @ApiResponse(responseCode = "500", description = "Возникли ошибки во время получения записи")
            })
    public Long getAmountSocks(@Parameter(description = "Цвет")
                               @RequestParam(required = false)
                               Colors color,
                               @Parameter(description = "Размер")
                               @RequestParam(required = false)
                               Sizes size,
                               @Parameter(description = "Минимальное значение хлопка в товаре")
                               @RequestParam(required = false)
                               Integer cottonMin,
                               @Parameter(description = "Максимальное значение хлопка в товаре")
                               @RequestParam(required = false)
                               Integer cottonMax) {
        return socksService.getAmountSocks(color, size, cottonMin, cottonMax);
    }

    /**
     * Ендпоинт выдачи носков по параметрам
     * @param color цвет
     * @param size размер
     * @param cottonPart содержание хлопка в товаре
     * @param quantity количество
     */
    @PutMapping("")
    @Operation(summary = "Выдача носков",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Носки выданы",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Socks.class))}),
                    @ApiResponse(responseCode = "500", description = "Возникли ошибки во время выдачи")
            })
    public ResponseEntity<Void> consumptionSocks(@Parameter(description = "Цвет")
                                                 @RequestParam
                                                 Colors color,
                                                 @Parameter(description = "Размер")
                                                 @RequestParam
                                                 Sizes size,
                                                 @Parameter(description = "Процентное содержание хлопка")
                                                 @RequestParam
                                                 int cottonPart,
                                                 @Parameter(description = "Количество")
                                                 @RequestParam
                                                 int quantity) {
        socksService.consumptionSocks(color, size, cottonPart, quantity);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Ендпоинт списание брака по параметрам
     * @param color цвет
     * @param size размер
     * @param cottonPart содержание хлопка
     * @param quantity количество
     */
    @DeleteMapping("")
    @Operation(summary = "Списание носков",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Носки списаны")})
    public ResponseEntity<Void> removeSocks(@Parameter(description = "Цвет")
                                            @RequestParam
                                            Colors color,
                                            @Parameter(description = "Размер")
                                            @RequestParam
                                            Sizes size,
                                            @Parameter(description = "Процентное содержание хлопка")
                                            @RequestParam
                                            int cottonPart,
                                            @Parameter(description = "Количество")
                                            @RequestParam
                                            int quantity) {

        socksService.removeSocks(color, size, cottonPart, quantity);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Скачивание файла json
     * @return файл json
     */
    @GetMapping("/download")
    @Operation(summary = "Скачивание json",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Запись получена"),
                    @ApiResponse(responseCode = "500", description = "Возникли ошибки во время получения записи")
            })
    public HttpEntity<byte[]> downloadSocks() {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        header.setContentLength(header.getContentLength());
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=socks.json");
        return new HttpEntity<>(socksService.downloadSocks(), header);
    }

    /**
     * Загрузка файла
     * @param file файл json
     */
    @PutMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "Загрузка файла",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Файл загружен"),
                    @ApiResponse(responseCode = "500", description = "Возникли ошибки во время сохранения")
            })
    public ResponseEntity<Void> upload(@Parameter(description = "Файл")
                                       @RequestPart
                                       MultipartFile file) {
        socksService.upload(file);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
